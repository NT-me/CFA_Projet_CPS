package com.cfaaato;

import com.connectors.*;
import com.data.*;
import com.port.*;
import com.services.*;
import com.utils.*;
import fr.sorbonne_u.components.*;
import fr.sorbonne_u.components.annotations.*;
import fr.sorbonne_u.components.exceptions.*;
import fr.sorbonne_u.components.helpers.*;

import java.util.*;

@RequiredInterfaces(required={RegistrationCI.class, CommunicationCI.class})
@OfferedInterfaces(offered = {CommunicationCI.class})
public class Participant extends AbstractComponent {

    protected ParticipantRegistrationOutboundPort prop;
    protected ParticipantCommunicationOutboundPort pcop;
    protected ParticipantCommunicationInboundPort pcip;
    protected ParticipantRoutageOutboundPort prtop;
    protected ParticipantRoutageInboundPort prtip;
    private ConnectionInfo myInformations;
    private Set<ConnectionInfo> neighbors;
    private HashMap<P2PAddressI, String> comAddressPortTable = new HashMap<P2PAddressI, String>();
    private HashMap<P2PAddressI, String> routingAddressPortTable = new HashMap<P2PAddressI, String>();
    private RoutingTable myRoutingTable = new RoutingTable();
    private Position pos;
    private Logger myLogger;

    protected Participant(int nbThreads, int nbSchedulableThreads, Position pos) throws Exception {
        super(nbThreads, nbSchedulableThreads);
        this.neighbors = new HashSet<ConnectionInfo>();
        this.pos = pos;
        //creation des ports de communication
        this.pcip = new ParticipantCommunicationInboundPort(UUID.randomUUID().toString(),this);
        this.pcop = new ParticipantCommunicationOutboundPort(this);
        //publication des ports de communication
        this.pcip.publishPort();
        this.pcop.publishPort();

        //creation des ports de routage
        this.prtip = new ParticipantRoutageInboundPort(UUID.randomUUID().toString(),this);
        this.prtop = new ParticipantRoutageOutboundPort(this);
        //publication des ports de routage
        this.prtip.publishPort();
        this.prtop.publishPort();

        myLogger = new Logger(Integer.toString(this.hashCode()));
        this.setLogger(myLogger);
    }

    public void registrateOnNetwork() throws Exception {
        this.doPortConnection(this.prop.getPortURI(), ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT, RegistrationConnector.class.getCanonicalName());

        this.prop = new ParticipantRegistrationOutboundPort(this);   //creation du port
        this.prop.publishPort();     //publication du port
        this.doPortConnection(this.prop.getPortURI(), ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT, RegistrationConnector.class.getCanonicalName());

        P2PAddress P2PAddress_init = new P2PAddress();
        ConnectionInfo myInfo_init = new ConnectionInfo(P2PAddress_init,
                this.pcip.getPortURI(),
                this.pos,
                ConstantsValues.RANGE_MAX_A,
                "0");
        this.myInformations = myInfo_init;

        //Iniitialisation de la liste de voisins directs
        this.neighbors = this.prop.registerInternal(
                this.myInformations.getAddress(),
                this.myInformations.getCommunicationInboundPortURI(),
                this.myInformations.getInitialPosition(),
                this.myInformations.getInitialRange(),
                this.myInformations.getRoutingInboundPortURI()
        );

        //Pour chaque voisin reçu du simulateur, on rempli notre table de port et on initialise notre table de routage
        for (ConnectionInfo coi : this.neighbors){
            this.comAddressPortTable.put(coi.getAddress(), coi.getCommunicationInboundPortURI());
            this.myRoutingTable.addNewNeighbor(coi.getAddress());
        }
    }

    public void newOnNetwork() throws Exception {
        //TODO se connecte aux anciens voisins -- seems ok

        this.logMessage(this.myInformations.getAddress().toString());
        this.logMessage(" nb neighbors : "+ this.neighbors.size());

        int Tour =1;
        for (ConnectionInfo coi : this.neighbors){
            this.logMessage(" Tour :"+Tour+ " neighbor :"+ coi.getAddress());

            this.doPortConnection(
                    this.pcop.getPortURI(),
                    coi.getCommunicationInboundPortURI(),
                    CommunicationConnector.class.getCanonicalName()
            );

            this.pcop.connect(
                    this.myInformations.getAddress(),
                    this.pcip.getPortURI(),
                    "");


            // this.comAddressPortTable.put(
            //         coi.getAddress(),
            //         coi.getCommunicationInboundPortURI()
            // );


            // this.routingAddressPortTable.put(
            //         coi.getAddress(),
            //         coi.getRoutingInboundPortURI()
            // );

            this.logMessage(" fin Tour :"+Tour+ " neighbor :"+ coi.getAddress());
            Tour++;
        }
    }

    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {


        if (!this.comAddressPortTable.containsKey(address)){

            this.logMessage("connect called from :"+ address);


            this.comAddressPortTable.put(address, communicationInboundPortURI);


            // this.doPortConnection(
            //         this.pcop.getPortURI(),
            //         communicationInboundPortURI,
            //         CommunicationConnector.class.getCanonicalName()
            // );
        }

        if (!this.routingAddressPortTable.containsKey(address)){
            this.routingAddressPortTable.put(address, routingInboundPortURI);
        }
        //routingtable en parametre doit etre celle du destinataire
        this.myRoutingTable.updateRouting(address, this.myRoutingTable.getRoutes(address));
    }




    public void floodMessageTransit(Message m) throws Exception {
        //if the message hit the receiver,
        if (m.getAddress().equals(this.myInformations.getAddress())){
            // System.out.println(
            //         this.myInformations.getAddress().toString()
            //                 + " | Msg name : "
            //                 + m
            //                 + " | Msg content : "
            //                 + m.getContent()
            // );
        }
        else {
            m.decrementHops();
            if (m.stillAlive()){
                // Iterating HashMap through for loop
                for (Map.Entry<P2PAddressI, String> item : this.comAddressPortTable.entrySet()) {
                    if(pcop.connected()){
                        pcop.doDisconnection();
                    }
                    this.doPortConnection(
                            this.pcop.getPortURI(),
                            item.getValue(),
                            CommunicationConnector.class.getCanonicalName()
                    );
                    // System.out.println(
                    //         this.myInformations.getAddress().toString()
                    //                 + " | Msg name : "
                    //                 + m
                    //                 + " | Msg send to : "
                    //                 + m.getAddress()
                    // );

                    this.pcop.routeMessage(m);
                }
                pcop.doDisconnection();

        if (!this.routingAddressPortTable.containsKey(address)){
            this.routingAddressPortTable.put(address, routingInboundPortURI);
        }

                this.logMessage("Msg died");
            }
        }
    }

    public void routeMessage(MessageI m) throws Exception {
        if (m instanceof MessageI){
            Message msg = (Message) m;
            floodMessageTransit(msg);
        }
    }


    //Les tables de routage vont être mise a jour
    public void updateNeighborsRoutingTable(){
        // System.out.println(this.myRoutingTable);
    }

    @Override
    public void start() throws ComponentStartException {
        super.start();
        try {
            this.pcip = new ParticipantCommunicationInboundPort(UUID.randomUUID().toString(),this);
            this.pcip.publishPort();
            this.pcop = new ParticipantCommunicationOutboundPort(this);   //creation du port
            this.pcop.publishPort();     //publication du port
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() throws Exception {


        // Random rd = new Random(); // creating Random object
        // rd.nextInt();

        // Thread.sleep(rd.nextInt(1000));
        // super.execute();
        this.toggleLogging();
        // this.logMessage(" Objet:"+ this.hashCode()+ " Start Execute");
        try {
            registrateOnNetwork();
            newOnNetwork();
            updateNeighborsRoutingTable();
            // System.out.println("nb voisins :"+ this.comAddressPortTable.size());
            if (this.comAddressPortTable.keySet().toArray().length > 0){
                Object randomName = this.comAddressPortTable.keySet().toArray()[new Random().nextInt(this.comAddressPortTable.keySet().toArray().length)];
                Message msg = new Message((AddressI) randomName);
                routeMessage(msg);
            }
            // System.out.println(this.neighbors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalise() throws Exception
    {
        this.logMessage(" nb comAddressPortTable : "+this.comAddressPortTable.size() +"\n"+this.comAddressPortTable );
        this.printExecutionLog();


        this.doPortDisconnection(this.prop.getPortURI());
        this.prop.unpublishPort();

        super.finalise();
    }
}
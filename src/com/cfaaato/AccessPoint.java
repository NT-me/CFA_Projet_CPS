package com.cfaaato;

import com.connectors.CommunicationConnector;
import com.connectors.RegistrationConnector;
import com.data.*;
import com.port.ParticipantCommunicationInboundPort;
import com.port.ParticipantCommunicationOutboundPort;
import com.port.ParticipantRegistrationOutboundPort;
import com.services.AddressI;
import com.services.IPAddressI;
import com.services.MessageI;
import com.services.P2PAddressI;
import com.utils.ConstantsValues;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

import java.util.*;

public class AccessPoint extends AbstractComponent {

    protected ParticipantRegistrationOutboundPort proap;
    protected ParticipantCommunicationOutboundPort pcoap;
    protected ParticipantCommunicationInboundPort pciap;
    private IPAddress adressIP;
    private P2PAddress adressP2P;
    private Set<ConnectionInfo> neighbors;
    private Position pos;
    private HashMap<P2PAddressI, String> comAddressPortTable = new HashMap<P2PAddressI, String>();
    private HashMap<P2PAddressI, String> routingAddressPortTable = new HashMap<P2PAddressI, String>();
    private RoutingTable myRoutingTable = new RoutingTable();
    private HashMap<IPAddressI, String> IPRoutingTable;

    protected AccessPoint(int nbThreads, int nbSchedulableThreads, Position pos) throws Exception {
        super(nbThreads, nbSchedulableThreads);

        this.neighbors = new HashSet<ConnectionInfo>();
        this.pos = pos;
        //creation des ports
        this.pciap = new ParticipantCommunicationInboundPort(UUID.randomUUID().toString(),this);
        this.pcoap = new ParticipantCommunicationOutboundPort(this);
        //publication des ports
        this.pciap.publishPort();
        this.pcoap.publishPort();

        this.adressP2P = new P2PAddress();
        this.adressIP = new IPAddress();

        this.IPRoutingTable = new HashMap<>();
    }

    public void registrateOnNetwork() throws Exception {
        this.proap = new ParticipantRegistrationOutboundPort(this);   //creation du port
        this.proap.publishPort();     //publication du port
        this.doPortConnection(
                this.proap.getPortURI(),
                ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT,
                RegistrationConnector.class.getCanonicalName()
        );

        //Iniitialisation de la liste de voisins directs
        this.neighbors = this.proap.registerAccessPoint(
                this.adressP2P,
                this.pciap.getPortURI(),
                pos,
                ConstantsValues.RANGE_MAX_A,
                "0"
        );

        //Pour chaque voisin reçu du simulateur, on rempli notre table de port et on initialise notre table de routage
        for (ConnectionInfo coi : this.neighbors){
            this.comAddressPortTable.put(coi.getAddress(), coi.getCommunicationInboundPortURI());
            this.myRoutingTable.addNewNeighbor(coi.getAddress());
        }
    }

    public void newOnNetwork() throws Exception {
        for (ConnectionInfo coi : this.neighbors){
            if (pcoap.connected()){
                pcoap.doDisconnection();
            }

            this.doPortConnection(
                    this.pcoap.getPortURI(),
                    coi.getCommunicationInboundPortURI(),
                    CommunicationConnector.class.getCanonicalName()
            );

            this.pcoap.connect(
                    this.adressP2P,
                    this.pciap.getPortURI(),
                    "");

            this.comAddressPortTable.put(
                    coi.getAddress(),
                    coi.getCommunicationInboundPortURI()
            );

            this.routingAddressPortTable.put(
                    coi.getAddress(),
                    coi.getRoutingInboundPortURI()
            );
        }
    }

    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {
        if (!this.comAddressPortTable.containsKey(address)){
            this.comAddressPortTable.put(address, communicationInboundPortURI);
            this.doPortConnection(
                    this.pcoap.getPortURI(),
                    communicationInboundPortURI,
                    CommunicationConnector.class.getCanonicalName()
            );
        }

        if (!this.routingAddressPortTable.containsKey(address)){
            this.routingAddressPortTable.put(address, routingInboundPortURI);
        }
        //routingtable en parametre doit etre celle du destinataire
        this.myRoutingTable.updateRouting(address, this.myRoutingTable.getRoutes(address));
    }


    public void floodMessageTransit(Message m) throws Exception {
/*        //if the message hit the receiver,
        if (m.getAddress().equals(this.adress)){
            System.out.println(
                    this.adress.toString()
                            + " | Msg name : "
                            + m.hashCode()
                            + " | Msg content : "
                            + m.getContent()
            );
        }
        else {
            m.decrementHops();
            if (m.stillAlive()){
                // Iterating HashMap through for loop
                for (Map.Entry<P2PAddressI, String> item : this.comAddressPortTable.entrySet()) {
                    if(pcoap.connected()){
                        pcoap.doDisconnection();
                    }
                    this.doPortConnection(
                            this.pcoap.getPortURI(),
                            item.getValue(),
                            CommunicationConnector.class.getCanonicalName()
                    );
                    System.out.println(
                            this.adress.toString()
                                    + " | Msg name : "
                                    + m.hashCode()
                                    + " | Msg send to : "
                                    + m.getAddress()
                    );
                    this.pcoap.routeMessage(m);
                }
                pcoap.doDisconnection();
            }
            else{
                System.out.println("Msg died");
            }
        }*/
    }

    public void routeMessage(MessageI m) throws Exception {
        if (m instanceof MessageI){
            Message msg = (Message) m;
            floodMessageTransit(msg);
        }
    }


    //Les tables de routage vont être mise a jour
    public void updateNeighborsRoutingTable(){
        System.out.println(this.myRoutingTable);
    }

    @Override
    public void start() throws ComponentStartException {
        super.start();
        try {

            this.proap = new ParticipantRegistrationOutboundPort(this);   //creation du port
            this.proap.publishPort();     //publication du port

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() throws Exception {
        super.execute();
        try {
            registrateOnNetwork();
            newOnNetwork();
            updateNeighborsRoutingTable();
            if (this.comAddressPortTable.keySet().toArray().length > 0){
                Object randomName = this.comAddressPortTable.keySet().toArray()[new Random().nextInt(this.comAddressPortTable.keySet().toArray().length)];
                Message msg = new Message((AddressI) randomName);
                routeMessage(msg);
            }
            //System.out.println(this.neighbors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalise() throws Exception
    {
/*        System.out.println(this.comAdressPortTable);*/
        System.out.println(this.neighbors);
        this.doPortDisconnection(this.proap.getPortURI());
        this.proap.unpublishPort();

        //this.doPortDisconnection(this.pip.getPortURI());
        this.pciap.unpublishPort();

        super.finalise();
    }

}

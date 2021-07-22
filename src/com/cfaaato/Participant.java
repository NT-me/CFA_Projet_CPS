package com.cfaaato;

import com.connectors.CommunicationConnector;
import com.connectors.RegistrationConnector;
import com.connectors.RoutageConnector;
import com.data.*;
import com.port.*;
import com.services.*;
import com.utils.ConstantsValues;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.helpers.Logger;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.io.File;
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
    private ConcurrentHashMap<P2PAddressI, String> comAddressPortTable = new ConcurrentHashMap<P2PAddressI, String>();
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
        myLogger = new Logger(Integer.toString(this.hashCode()));
        this.setLogger(myLogger); 

        //creation des ports de routage
        this.prtip = new ParticipantRoutageInboundPort(UUID.randomUUID().toString(),this);
        this.prtop = new ParticipantRoutageOutboundPort(this);
        //publication des ports de routage
        this.prtip.publishPort();
        this.prtop.publishPort();
    }

    public void registrateOnNetwork() throws Exception {
        this.doPortConnection(this.prop.getPortURI(), ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT, RegistrationConnector.class.getCanonicalName());

        P2PAddress P2PAddress_init = new P2PAddress();
        ConnectionInfo myInfo_init = new ConnectionInfo(P2PAddress_init,
                this.pcip.getPortURI(),
                this.pos,
                ConstantsValues.RANGE_MAX_A,
                this.prtip.getPortURI());
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
        }

        this.myRoutingTable.addNeighbors(this.myInformations.getAddress(),this.neighbors);

    }

    public void newOnNetwork() throws Exception {

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
                    this.prtip.getPortURI());


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
        initialiseRoutesNeighbor(address,this.myRoutingTable);
    }

    public void initialiseRoutesNeighbor(P2PAddressI address, RoutingTable rt) throws Exception {
        String neighborInboundPortURI = this.routingAddressPortTable.get(address);
        this.doPortConnection(
                this.prtop.getPortURI(),
                neighborInboundPortURI,
                RoutageConnector.class.getCanonicalName()
        );
        this.updateRouting(address, rt.getRoutes(address));
        backUpdateConnectedNeighbor(address,this.myRoutingTable);
    }

    public void backUpdateConnectedNeighbor(P2PAddressI address, RoutingTable rt) throws Exception {
        String neighborInboundPortURI = this.routingAddressPortTable.get(address);
        this.doPortConnection(
                this.prtop.getPortURI(),
                neighborInboundPortURI,
                RoutageConnector.class.getCanonicalName()
        );
        this.updateRouting(address, rt.getRoutes(address));
    }




    public void floodMessageTransit(Message m) throws Exception {
        //if the message hit the receiver,
        if (m.getAddress().equals(this.myInformations.getAddress())){
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


                    this.pcop.routeMessage(m);
                }
                pcop.doDisconnection();

            }
            else{

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

    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception{
        HashMap<P2PAddressI,Set<RouteInfo>> map = this.myRoutingTable.getTable();
        Set<P2PAddressI> allNeighbor = new HashSet<>();
        for (Map.Entry<P2PAddressI,Set<RouteInfo>> table: map.entrySet()) {
            allNeighbor.add(table.getKey());
        }
        //System.out.println("mon adresse: " + this.myInformations.getAddress() + " neighbor " +map);
    }

    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception{

    }
    //Les tables de routage vont être mise a jour

    public void updateNeighborsRoutingTable() throws Exception {
        HashMap<P2PAddressI,Set<RouteInfo>> map = this.myRoutingTable.getTable();
        System.out.println("bonjour mon adresse: " + this.myInformations.getAddress());
        for (Map.Entry<P2PAddressI,Set<RouteInfo>> table: map.entrySet()) {
            System.out.println("mon adresse: " + this.myInformations.getAddress());
            Set<RouteInfo> routeInfo = table.getValue();
            this.doPortConnection(
                    this.prtop.getPortURI(),
                    this.routingAddressPortTable.get(table.getKey()),
                    RoutageConnector.class.getCanonicalName()
            );
            this.prtop.updateRouting(table.getKey(),routeInfo);
        }
    }

    @Override
    public void start() throws ComponentStartException {
        super.start();
        
        try {
            this.prop = new ParticipantRegistrationOutboundPort(this);   //creation du port
            this.prop.publishPort();     //publication du port

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
            if (this.comAddressPortTable.keySet().toArray().length > 0){
                Object randomName = this.comAddressPortTable.keySet().toArray()[new Random().nextInt(this.comAddressPortTable.keySet().toArray().length)];
                Message msg = new Message((AddressI) randomName);
                routeMessage(msg);
            }
            updateNeighborsRoutingTable();
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalise() throws Exception
    {
        this.logMessage(" nb comAddressPortTable : "+this.comAddressPortTable.size() +"\n"+this.comAddressPortTable );
        this.printExecutionLog();
        //System.out.println(this.comAdressPortTable);
        //System.out.println("mon adresse: " + this.);
//        System.out.println("mon portInbound: " + this.prtip.getPortURI());
//        System.out.println("ma routingTablePort: " + this.routingAddressPortTable);
        HashMap<P2PAddressI,Set<RouteInfo>> map = this.myRoutingTable.getTable();
        System.out.println(map);
        for (Map.Entry<P2PAddressI,Set<RouteInfo>> table: map.entrySet()) {
            //System.out.println("mes routes: " + this.myRoutingTable.getRoutes(table.getKey()));
            Set<RouteInfo> routeInfo = table.getValue();
            Iterator<RouteInfo> iterator = routeInfo.iterator();
            while(iterator.hasNext()) {//Looping throught the collection
                RouteInfo actual = iterator.next();
                //System.out.println("mon adresse: " + this.myInformations.getAddress() + "mes voisins: " + table.getKey() + " ma destination " + actual.getDestination() + "");
            }
        }


        this.doPortDisconnection(this.prop.getPortURI());
        this.prop.unpublishPort();

        super.finalise();
    }


}

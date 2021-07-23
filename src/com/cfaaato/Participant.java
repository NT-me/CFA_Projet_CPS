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
import java.util.concurrent.ConcurrentHashMap;

@RequiredInterfaces(required={RegistrationCI.class, CommunicationCI.class})
@OfferedInterfaces(offered = {CommunicationCI.class})
public class Participant extends AbstractComponent {

    //Ports declaration
    protected ParticipantRegistrationOutboundPort prop;
    protected ParticipantCommunicationOutboundPort pcop;
    protected ParticipantCommunicationInboundPort pcip;
    protected ParticipantRoutageInboundPort prtip;
    protected ParticipantRoutageOutboundPort prtop;

    //Cnnectors informations
    RoutageConnector routageConnector;

    //Connections Informations of this element
    private ConnectionInfo myInformations;
    private Set<ConnectionInfo> neighbors;
    private ConcurrentHashMap<P2PAddressI, String> comAddressPortTable = new ConcurrentHashMap<P2PAddressI, String>();
    private ConcurrentHashMap<P2PAddressI, String> routingAddressPortTable = new ConcurrentHashMap<P2PAddressI, String>();
    private RoutingTable myRoutingTable = new RoutingTable();
    private Position pos;
    private Logger myLogger;

    protected Participant(int nbThreads, int nbSchedulableThreads, Position pos) throws Exception {
        super(nbThreads, nbSchedulableThreads);
        this.neighbors = new HashSet<>();
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
        this.prop = new ParticipantRegistrationOutboundPort(this);   //creation du port
        this.prop.publishPort();     //publication du port
        this.doPortConnection(this.prop.getPortURI(), ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT, RegistrationConnector.class.getCanonicalName());

        P2PAddress P2PAddress_init = new P2PAddress();
        this.myInformations = new ConnectionInfo(P2PAddress_init,
                this.pcip.getPortURI(),
                this.pos,
                ConstantsValues.RANGE_MAX_A,
                this.prtip.getPortURI());
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
                    this.prtip.getPortURI());


             this.comAddressPortTable.put(
                     coi.getAddress(),
                     coi.getCommunicationInboundPortURI()
             );

      
             this.routingAddressPortTable.put(
                     coi.getAddress(),
                     coi.getRoutingInboundPortURI()
             );

            this.logMessage(" fin Tour :"+Tour+ " neighbor :"+ coi.getAddress());
            Tour++;
        }
    }

    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {
        if (!this.comAddressPortTable.containsKey(address)){
            this.logMessage("connect called from :"+ address);
            this.comAddressPortTable.put(address, communicationInboundPortURI);

             this.doPortConnection(
                     this.pcop.getPortURI(),
                     communicationInboundPortURI,
                     CommunicationConnector.class.getCanonicalName()
             );
        }

        if (!this.routingAddressPortTable.containsKey(address)){
            this.routingAddressPortTable.put(address, routingInboundPortURI);
        }
    }

    public void floodMessageTransit(Message m) throws Exception {
        //if the message hit the receiver, it's done
        if (m.getAddress().equals(this.myInformations.getAddress())){
            myLogger.logMessage("Message arrived to destination by flood");
        }
        else {
            m.decrementHops();
            if (m.stillAlive()){
                //Iterating through HashMap
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
                this.logMessage("Msg died");
            }
        }
    }

    /*this method should parse all address contained inside the table attribut
     */
    public void routeByTable(Message m) throws Exception {
        if(this.myInformations.getAddress().equals(m.getAddress())){
            myLogger.logMessage(
                    this.myInformations.getAddress().toString()
                            + " | Msg name : "
                            + m.hashCode()
                            + " | Msg send to : "
                            + m.getAddress()
            );
            myLogger.logMessage("Message arrived to destination by rooting");
        }
        if(this.myRoutingTable.getTable().isEmpty()){
            myLogger.logMessage("routing table of "+this.myInformations.getAddress()+" is empty");
            floodMessageTransit(m);
            return;
        }
        m.decrementHops();
        int nbrHops = Integer.MAX_VALUE;
        //if the receiver is present on the neighbors table
        if(m.stillAlive() && this.myRoutingTable.getTable().keySet().contains(m.getAddress())){
            this.doPortConnection(this.pcop.getPortURI(),this.comAddressPortTable.get(m.getAddress())
                    ,CommunicationConnector.class.getCanonicalName());
            this.pcop.routeMessage(m);
        }
        //Parsing the
        AddressI next = null;
        HashMap<P2PAddressI,Set<RouteInfo>> map = this.myRoutingTable.getTable();
        for (Map.Entry<P2PAddressI,Set<RouteInfo>> table: map.entrySet()) {
            Set<RouteInfo> routeInfo = table.getValue();
            Iterator<RouteInfo> iterator = routeInfo.iterator();
            while(iterator.hasNext()){//Looping throught the collection
                RouteInfo actual = iterator.next();
                if(actual.getDestination().equals(m.getAddress())
                        && actual.getNumberOfHops() < nbrHops){
                    nbrHops = actual.getNumberOfHops();
                    next = table.getKey();
                }
            }
        }
        //If the value has not been updated, aka the aimed node is not known
        if(nbrHops == Integer.MAX_VALUE){
            floodMessageTransit(m);
            return;
        }
        this.doPortConnection(this.pcop.getPortURI(),this.comAddressPortTable.get(next)
                ,CommunicationConnector.class.getCanonicalName());
        this.pcop.routeMessage(m);
    }

    public void routeMessage(MessageI m) throws Exception {
        if (m instanceof MessageI){
            Message msg = (Message) m;
            routeByTable(msg);
        }
    }

    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception{
        HashMap<P2PAddressI,Set<RouteInfo>> map = this.myRoutingTable.getTable();
        boolean isPresent = false;
        for (Map.Entry<P2PAddressI,Set<RouteInfo>> table: map.entrySet()) {
            if (table.getKey().equals(neighbour)){
                isPresent = true;
                Set<RouteInfo> myroutes = table.getValue();
                Iterator<RouteInfo> myiterator = myroutes.iterator();
                while(myiterator.hasNext()) {
                    RouteInfo myactualroute = myiterator.next();
                    Iterator<RouteInfo> newiterator = routes.iterator();
                    while (newiterator.hasNext()) {
                        RouteInfo newactualroute = newiterator.next();
                        if (myactualroute.getDestination().equals(newactualroute.getDestination())) {
                            if (myactualroute.getNumberOfHops() > newactualroute.getNumberOfHops()) {
                                this.myRoutingTable.updateSetRoute(neighbour, newactualroute);
                            }
                        }
                        else {
                            this.myRoutingTable.updateNewRoute(neighbour,newactualroute);
                        }
                    }
                }
            }
        }
        if (!isPresent && !this.myInformations.getAddress().equals(neighbour)){
            this.myRoutingTable.addNewAddressRoutes(neighbour,routes);
        }

    }

    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception{

    }
    //Les tables de routage vont être mise a jour

    public void updateNeighborsRoutingTableBegin() throws Exception {
        HashMap<P2PAddressI,Set<RouteInfo>> map = this.myRoutingTable.getTable();
        if (!map.isEmpty()) {
            for (Map.Entry<P2PAddressI, Set<RouteInfo>> table : map.entrySet()) {
                this.doPortConnection(
                        this.prtop.getPortURI(),
                        this.routingAddressPortTable.get(table.getKey()),
                        RoutageConnector.class.getCanonicalName()
                );
                RouteInfo myRoute = new RouteInfo(table.getKey(),1);
                Set<RouteInfo> monSetdeRoutes = new HashSet<>();
                monSetdeRoutes.add(myRoute);
                this.prtop.updateRouting(this.myInformations.getAddress(), monSetdeRoutes);

            }
        }
    }

    public void updateNeighborsRoutingTableWithOther() throws Exception {
        HashMap<P2PAddressI,Set<RouteInfo>> map = this.myRoutingTable.getTable();
        P2PAddressI address = new P2PAddress();
        Set<RouteInfo> routeInfo = new HashSet<>();
        boolean boucle = false;
        if (!map.isEmpty()) {
            for (Map.Entry<P2PAddressI, Set<RouteInfo>> table : map.entrySet()) {
                if(!boucle) {
                    address = table.getKey();
                    routeInfo = table.getValue();
                    boucle = true;
                }
                if(boucle) {
                    this.doPortConnection(
                            this.prtop.getPortURI(),
                            this.routingAddressPortTable.get(table.getKey()),
                            RoutageConnector.class.getCanonicalName()
                    );
                    this.prtop.updateRouting(address, routeInfo);
                }
            }
            boucle = false;
            for (Map.Entry<P2PAddressI, Set<RouteInfo>> table : map.entrySet()) {
                if(!boucle) {
                    address = table.getKey();
                    boucle = true;
                }
                if(boucle) {
                    this.doPortConnection(
                            this.prtop.getPortURI(),
                            this.routingAddressPortTable.get(address),
                            RoutageConnector.class.getCanonicalName()
                    );

                    this.prtop.updateRouting(table.getKey(), table.getValue());
                }
            }
        }
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
        try {
            registrateOnNetwork();
            newOnNetwork();

            Thread.sleep(1000);
            updateNeighborsRoutingTableBegin();
            Thread.sleep(1000);
            updateNeighborsRoutingTableWithOther();
            Thread.sleep(1000);

            if (this.comAddressPortTable.keySet().toArray().length > 0){
                Object randomName = this.comAddressPortTable.keySet().toArray()[new Random().nextInt(this.comAddressPortTable.keySet().toArray().length)];
                Message msg = new Message((AddressI) randomName);
                routeMessage(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalise() throws Exception
    {
        this.logMessage(" nb comAddressPortTable : "+this.comAddressPortTable.size() +"\n"+this.comAddressPortTable );

        HashMap<P2PAddressI,Set<RouteInfo>> map = this.myRoutingTable.getTable();
        this.logMessage("ma rt: " + map);
        for (Map.Entry<P2PAddressI,Set<RouteInfo>> table: map.entrySet()) {
            Set<RouteInfo> routeInfo = table.getValue();
            Iterator<RouteInfo> iterator = routeInfo.iterator();
            while(iterator.hasNext()) {
                RouteInfo actual = iterator.next();
                this.logMessage(actual + " allo " + table.getKey() + " test " + actual.getDestination() + "  " + actual.getNumberOfHops());
            }
        }
        this.printExecutionLog();
        this.doPortDisconnection(this.prop.getPortURI());
        this.prop.unpublishPort();
        super.finalise();
    }
}
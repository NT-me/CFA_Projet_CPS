package com.cfaaato;

import com.connectors.CommunicationConnector;
import com.connectors.RegistrationConnector;
import com.data.*;
import com.port.ParticipantCommunicationOutboundPort;
import com.port.ParticipantCommunicationInboundPort;
import com.port.ParticipantRegistrationOutboundPort;
import com.services.*;
import com.utils.ConstantsValues;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

import java.util.*;

@RequiredInterfaces(required={RegistrationCI.class, CommunicationCI.class})
@OfferedInterfaces(offered = {CommunicationCI.class})
public class Participant extends AbstractComponent {

    protected ParticipantRegistrationOutboundPort pop;
    protected ParticipantCommunicationOutboundPort pcop;
    protected ParticipantCommunicationInboundPort pip;
    private ConnectionInfo myInformations;
    private Set<ConnectionInfo> neighbors;
    private HashMap<P2PAddressI, String> comAdressPortTable = new HashMap<P2PAddressI, String>();
    private HashMap<P2PAddressI, String> routingAdressPortTable = new HashMap<P2PAddressI, String>();
    private Position pos;

    protected Participant(int nbThreads, int nbSchedulableThreads, Position pos) throws Exception {
        super(nbThreads, nbSchedulableThreads);
        this.neighbors = new HashSet<ConnectionInfo>();
        this.pos = pos;
        this.pip = new ParticipantCommunicationInboundPort(UUID.randomUUID().toString(),this);
        this.pip.publishPort();
        this.pcop = new ParticipantCommunicationOutboundPort(this);   //creation du port
        this.pcop.publishPort();     //publication du port
    }

    protected Participant(String reflectionInboundPortURI, int nbThreads, int nbSchedulableThreads, Position pos) throws Exception {
        super(reflectionInboundPortURI, nbThreads, nbSchedulableThreads);
        this.pos = pos;
    }

    public void registrateOnNetwork() throws Exception {

        this.pop = new ParticipantRegistrationOutboundPort(this);   //creation du port
        this.pop.publishPort();     //publication du port
        this.doPortConnection(this.pop.getPortURI(), ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT, RegistrationConnector.class.getCanonicalName());

        P2PAddress P2PAdress_init = new P2PAddress();
        //Position pos = new Position(3, 4);
        ConnectionInfo myInfo_init = new ConnectionInfo(P2PAdress_init,
                this.pip.getPortURI(),
                this.pos,
                ConstantsValues.RANGE_MAX_A,
                "0");
        this.myInformations = myInfo_init;

        this.neighbors = this.pop.registerInternal(
                this.myInformations.getAddress(),
                this.myInformations.getCommunicationInboundPortURI(),
                this.myInformations.getInitialPosition(),
                this.myInformations.getInitialRange(),
                this.myInformations.getRoutingInboundPortURI()
        );

        for (ConnectionInfo coi : this.neighbors){
            this.comAdressPortTable.put(coi.getAddress(), coi.getCommunicationInboundPortURI());
        }
    }

    public void newOnNetwork() throws Exception {
        for (ConnectionInfo coi : this.neighbors){

            this.doPortConnection(
                    this.pcop.getPortURI(),
                    coi.getCommunicationInboundPortURI(),
                    CommunicationConnector.class.getCanonicalName()
            );

            this.pcop.connect(
                    this.myInformations.getAddress(),
                    this.pip.getPortURI(),
                    "");

            this.comAdressPortTable.put(
                    coi.getAddress(),
                    coi.getCommunicationInboundPortURI()
            );

            this.routingAdressPortTable.put(
                    coi.getAddress(),
                    coi.getRoutingInboundPortURI()
            );
        }
    }

    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {
        if (!this.comAdressPortTable.containsKey(address)){
            this.comAdressPortTable.put(address, communicationInboundPortURI);
            this.doPortConnection(
                    this.pcop.getPortURI(),
                    communicationInboundPortURI,
                    CommunicationConnector.class.getCanonicalName()
            );
        }

        if (!this.routingAdressPortTable.containsKey(address)){
            this.routingAdressPortTable.put(address, routingInboundPortURI);
        }

    }

public void floodMessageTransit(MessageI m) throws Exception {
        if (m.getAddress().equals(this.myInformations.getAddress())){
            System.out.println(
                    this.myInformations.getAddress().toString()
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
                for (Map.Entry<P2PAddressI, String> item : this.comAdressPortTable.entrySet()) {
                    if(pcop.connected()){
                        pcop.doDisconnection();
                    }
                    this.doPortConnection(
                            this.pcop.getPortURI(),
                            item.getValue(),
                            CommunicationConnector.class.getCanonicalName()
                    );
                    System.out.println(
                            this.myInformations.getAddress().toString()
                                    + " | Msg name : "
                                    + m.hashCode()
                                    + " | Msg send to : "
                                    + m.getAddress()
                    );

                    this.pcop.routeMessage(m);
                }
                pcop.doDisconnection();

            }
            else{

                System.out.println("Msg died");
            }
        }
    }

    public void routeMessage(MessageI m) throws Exception {
        if (m instanceof MessageI){
            floodMessageTransit(m);
        }
    }


    @Override
    public void start() throws ComponentStartException {
        super.start();
        try {

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
            if (this.comAdressPortTable.keySet().toArray().length > 0){
                Object randomName = this.comAdressPortTable.keySet().toArray()[new Random().nextInt(this.comAdressPortTable.keySet().toArray().length)];
                Message msg = new Message((AddressI) randomName);

                routeMessage(msg);
            }
            System.out.println(this.neighbors);
            //System.out.println(this.comAdressPortTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalise() throws Exception
    {
        //System.out.println(this.comAdressPortTable);




        this.doPortDisconnection(this.pop.getPortURI());
        this.pop.unpublishPort();

        //this.doPortDisconnection(this.pip.getPortURI());
        this.pip.unpublishPort();

        super.finalise();
    }


}

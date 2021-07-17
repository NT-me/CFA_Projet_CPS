package com.cfaaato;

import com.connectors.CommunicationConnector;
import com.connectors.RegistrationConnector;
import com.data.ConnectionInfo;
import com.data.P2PAddress;
import com.data.Position;
import com.data.RoutingTable;
import com.port.ParticipantCommunicationOutboundPort;
import com.port.ParticipantCommunicationInboundPort;
import com.port.ParticipantRegistrationOutboundPort;
import com.services.CommunicationCI;
import com.services.P2PAddressI;
import com.services.RegistrationCI;
import com.utils.ConstantsValues;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    private RoutingTable myRoutingTable = new RoutingTable();
    private Position pos;

    protected Participant(int nbThreads, int nbSchedulableThreads, Position pos) throws Exception {
        super(nbThreads, nbSchedulableThreads);
        this.neighbors = new HashSet<ConnectionInfo>();
        this.pos = pos;
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

        //Pour chaque voisin reçu du simulateur, on rempli notre table de port et on initialise notre table de routage
        for (ConnectionInfo coi : this.neighbors){
            this.comAdressPortTable.put(coi.getAddress(), coi.getCommunicationInboundPortURI());
            this.myRoutingTable.addNewNeighbor(coi.getAddress());
        }
    }

    public void newOnNetwork() throws Exception {
        //TODO se connecte aux anciens voisins -- seems ok

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
        //TODO ajouter les nouveaux voisins + se connecter à eux et préparer les ports
        // AJOUTER DANS LES TABLEAUX DE CONNECTIONINFO LES NOUVEAUX VOISINS (SI POSSIBLE CONNECTIONINFO)
        //System.out.println("coucou");
        System.out.println(this.myInformations.getCommunicationInboundPortURI() + " | "+ communicationInboundPortURI);
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
        //routingtable en parametre doit etre celle du destinataire
        this.myRoutingTable.updateRouting(address, this.myRoutingTable.getRoutes(address));
    }
    //Les tables de routage vont être mise a jour
    public void updateNeighborsRoutingTable(){
        System.out.println(this.myRoutingTable);
    }
    @Override
    public void start() throws ComponentStartException {
        super.start();
        try {
            this.pip = new ParticipantCommunicationInboundPort(UUID.randomUUID().toString(),this);
            this.pip.publishPort();
            this.pcop = new ParticipantCommunicationOutboundPort(this);   //creation du port
            this.pcop.publishPort();     //publication du port
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
            //System.out.println(this.neighbors);
            //System.out.println(this.comAdressPortTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalise() throws Exception
    {
        System.out.println(this.comAdressPortTable);
        this.doPortDisconnection(this.pop.getPortURI());
        this.pop.unpublishPort();

//        this.doPortDisconnection(this.pip.getPortURI());
        this.pip.unpublishPort();

        super.finalise();
    }
}

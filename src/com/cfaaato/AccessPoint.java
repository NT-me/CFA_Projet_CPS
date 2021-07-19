package com.cfaaato;

import com.connectors.RegistrationConnector;
import com.data.ConnectionInfo;
import com.data.P2PAddress;
import com.data.Position;
import com.port.ParticipantCommunicationInboundPort;
import com.port.ParticipantCommunicationOutboundPort;
import com.port.ParticipantRegistrationOutboundPort;
import com.utils.ConstantsValues;
import fr.sorbonne_u.components.AbstractComponent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AccessPoint extends AbstractComponent {

    protected ParticipantRegistrationOutboundPort proap;
    protected ParticipantCommunicationOutboundPort pcoap;
    protected ParticipantCommunicationInboundPort pciap;
    private ConnectionInfo myInformations;
    private Set<ConnectionInfo> neighbors;
    private Position pos;

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
    }

    public void registrateOnNetwork() throws Exception {
        this.proap = new ParticipantRegistrationOutboundPort(this);   //creation du port
        this.proap.publishPort();     //publication du port
        this.doPortConnection(this.proap.getPortURI(), ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT, RegistrationConnector.class.getCanonicalName());

        P2PAddress P2PAddress_init = new P2PAddress();
        ConnectionInfo myInfo_init = new ConnectionInfo(P2PAddress_init,
                this.pciap.getPortURI(),
                this.pos,
                ConstantsValues.RANGE_MAX_A,
                "0");
        this.myInformations = myInfo_init;

        //Iniitialisation de la liste de voisins directs
        this.neighbors = this.proap.registerInternal(
                this.myInformations.getAddress(),
                this.myInformations.getCommunicationInboundPortURI(),
                this.myInformations.getInitialPosition(),
                this.myInformations.getInitialRange(),
                this.myInformations.getRoutingInboundPortURI()
        );

        //Pour chaque voisin reçu du simulateur, on rempli notre table de port et on initialise notre table de routage
        /*for (ConnectionInfo coi : this.neighbors){
          *  this.comAddressPortTable.put(coi.getAddress(), coi.getCommunicationInboundPortURI());
            this.myRoutingTable.addNewNeighbor(coi.getAddress());
        }*/
    }
}

package com.plugins;

import com.cfaaato.Participant;
import com.connectors.RegistrationConnector;
import com.data.ConnectionInfo;
import com.data.P2PAddress;
import com.port.ParticipantRegistrationOutboundPort;
import com.services.CommunicationCI;
import com.services.RegistrationCI;
import com.utils.ConstantsValues;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;

public class P2PDevice extends AbstractPlugin {
    public P2PDevice()
    {
        super();
    }

    public void registrateOnNetwork() throws Exception {
        this.getOwner().doPortConnection(
                ((Participant)this.getOwner())
                        .getProp()
                        .getPortURI(),
                ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT,
                RegistrationConnector.class.getCanonicalName());

        P2PAddress P2PAddress_init = new P2PAddress();
        ConnectionInfo myInfo_init = new ConnectionInfo(P2PAddress_init,
                ((Participant) this.getOwner()).getPcip().getPortURI(),
                ((Participant) this.getOwner()).getPos(),
                ConstantsValues.RANGE_MAX_A,
                "0");
        ((Participant) this.getOwner()).setMyInformations(myInfo_init);

        //Iniitialisation de la liste de voisins directs
        ((Participant) this.getOwner()).setNeighbors(
                ((Participant) this.getOwner()).getProp().registerInternal(
                        ((Participant) this.getOwner()).getMyInformations().getAddress(),
                        ((Participant) this.getOwner()).getMyInformations().getCommunicationInboundPortURI(),
                        ((Participant) this.getOwner()).getMyInformations().getInitialPosition(),
                        ((Participant) this.getOwner()).getMyInformations().getInitialRange(),
                        ((Participant) this.getOwner()).getMyInformations().getRoutingInboundPortURI()
                )
        );

        //Pour chaque voisin reçu du simulateur, on rempli notre table de port et on initialise notre table de routage
        for (ConnectionInfo coi : ((Participant) this.getOwner()).getNeighbors()){
            ((Participant) this.getOwner()).getComAddressPortTable().put(coi.getAddress(), coi.getCommunicationInboundPortURI());
            ((Participant) this.getOwner()).getMyRoutingTable().addNewNeighbor(coi.getAddress());
        }
    }



    @Override
    public void installOn(ComponentI owner) throws Exception
    {
        super.installOn(owner);
        assert owner instanceof Participant;

        this.addRequiredInterface(RegistrationCI.class);
        this.addRequiredInterface(CommunicationCI.class);
        this.addOfferedInterface(CommunicationCI.class);


    }

    @Override
    public void initialise() throws Exception {
        //registrateOnNetwork();
    }

}

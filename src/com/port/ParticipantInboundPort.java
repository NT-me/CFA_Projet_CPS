package com.port;

import com.cfaaato.Participant;
import com.cfaaato.Simulator;
import com.services.CommunicationCI;
import com.services.MessageI;
import com.services.P2PAddressI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class ParticipantInboundPort  extends AbstractOutboundPort implements CommunicationCI {
    public ParticipantInboundPort(String uri, Class<? extends RequiredCI> implementedInterface, ComponentI owner) throws Exception {
        super(uri, implementedInterface, owner);
    }

    public ParticipantInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, CommunicationCI.class, owner);
    }

    public ParticipantInboundPort(Class<? extends RequiredCI> implementedInterface, ComponentI owner) throws Exception {
        super(implementedInterface, owner);
    }



    @Override
    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {
        this.getOwner().handleRequest(
                p -> {
                    try {
                        ((Participant) p).connect(address, communicationInboundPortURI, routingInboundPortURI);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });
    }
    /*
    @Override
    public void routeMessage(MessageI m) {

    }

    @Override
    public void ping() {

    }
*/
}

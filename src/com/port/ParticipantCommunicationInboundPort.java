package com.port;

import com.cfaaato.Participant;
import com.cfaaato.Simulator;
import com.services.CommunicationCI;
import com.services.MessageI;
import com.services.P2PAddressI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class ParticipantCommunicationInboundPort extends AbstractOutboundPort implements CommunicationCI {
    public ParticipantCommunicationInboundPort(String uri, Class<? extends RequiredCI> implementedInterface, ComponentI owner) throws Exception {
        super(uri, implementedInterface, owner);
    }

    public ParticipantCommunicationInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, CommunicationCI.class, owner);
    }

    public ParticipantCommunicationInboundPort(Class<? extends RequiredCI> implementedInterface, ComponentI owner) throws Exception {
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

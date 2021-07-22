package com.port;

import com.services.CommunicationCI;
import com.services.MessageI;
import com.services.P2PAddressI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class ParticipantCommunicationOutboundPort extends AbstractOutboundPort implements CommunicationCI{
    public ParticipantCommunicationOutboundPort(ComponentI owner) throws Exception {
        super(CommunicationCI.class, owner);
    }

    @Override
    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {
        ((CommunicationCI)this.getConnector()).connect(
                address,
                communicationInboundPortURI,
                routingInboundPortURI);
    }

    @Override
    public void routeMessage(MessageI m) throws Exception {
        ((CommunicationCI)this.getConnector()).routeMessage(m);
    }
}
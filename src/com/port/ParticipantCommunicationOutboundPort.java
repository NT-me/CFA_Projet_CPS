package com.port;

import com.services.CommunicationCI;
import com.services.MessageI;
import com.services.P2PAddressI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * The type Participant communication outbound port.
 */
public class ParticipantCommunicationOutboundPort extends AbstractOutboundPort implements CommunicationCI{
    /**
     * Description: Instantiates a new Participant communication outbound port.
     * @param owner of type ComponentI - the owner of that port
     * @throws Exception the exception
     */
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
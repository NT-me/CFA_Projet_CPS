package com.connectors;

import com.services.CommunicationCI;
import com.services.P2PAddressI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class CommunicationConnector extends AbstractConnector implements CommunicationCI {
    @Override
    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {
        ((CommunicationCI)this.offering).connect(
                address,
                communicationInboundPortURI,
                routingInboundPortURI
        );
    }
}

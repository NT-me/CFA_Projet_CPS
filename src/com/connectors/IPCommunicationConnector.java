package com.connectors;

import com.services.*;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class IPCommunicationConnector extends AbstractConnector implements IPCommunicationCI {

    @Override
    public void connectToServer(IPAddressI address, String communicationPortURI) throws Exception {
        ((IPCommunicationCI)this.offering).connectToServer(
                address,
                communicationPortURI
        );
    }

    @Override
    public void routeMessage(MessageI m) throws Exception {
        ((IPCommunicationCI)this.offering).routeMessage(m);
    }
}

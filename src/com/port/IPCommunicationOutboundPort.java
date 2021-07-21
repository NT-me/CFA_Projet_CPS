package com.port;

import com.services.IPAddressI;
import com.services.IPCommunicationCI;
import com.services.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class IPCommunicationOutboundPort extends AbstractOutboundPort implements IPCommunicationCI {
    public IPCommunicationOutboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, IPCommunicationCI.class, owner);
    }

    @Override
    public void connectToServer(IPAddressI address, String communicationPortURI) throws Exception {
        ((IPCommunicationCI)this.getConnector()).connectToServer(
                address,
                communicationPortURI
        );
    }

    @Override
    public void routeMessage(MessageI m) throws Exception {
        ((IPCommunicationCI)this.getConnector()).routeMessage(m);
    }
}

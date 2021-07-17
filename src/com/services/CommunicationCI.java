package com.services;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface CommunicationCI extends RequiredCI, OfferedCI {
    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception;
/*
    public void routeMessage(MessageI m);

    public void ping();

 */
}

package com.services;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface IPCommunicationCI extends RequiredCI, OfferedCI {
    public void connectToServer(IPAddressI address, String communicationPortURI) throws Exception;

    public void routeMessage(MessageI m) throws Exception;
}

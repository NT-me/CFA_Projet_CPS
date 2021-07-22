package com.services;

import fr.sorbonne_u.components.interfaces.*;

public interface CommunicationCI extends RequiredCI, OfferedCI {

    /** TODO remplir la javadoc
     * Description : Connection protocol to bound two devices, two ports, or two connectors
     * @param address of type P2PAddressI - The address of the receiver
     * @param communicationInboundPortURI of type String - the communicationInboundPort to connect to
     * @param routingInboundPortURI of type String - the routingInboundPort to connect to
     * @throws Exception
     */
    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception;

    /**
     * Decription : Send a message to one or many receivers
     * @param m of type MessageI - The message (#GrandMasterFlash) that we want to send
     * @throws Exception
     */
    public void routeMessage(MessageI m) throws Exception;

    /**
     * Description : ping the neighbors of the calling element
     */
    //public void ping() throws ConnectException;
}
package com.data;

import com.services.P2PAddressI;

public class ConnectionInfo {

    private P2PAddress address;
    private String communicationUnboudPortUri;
    private Position initialPosition;
    private double initialRange;
    private String routingInboundPortUri;

    public P2PAddressI getAddress(){
        return this.address;
    }

    public String getCommunicationInboundPortURI(){
        return this.communicationUnboudPortUri;
    }

    public String getRoutingInboundPortURI(){
        return this.routingInboundPortUri;
    }
}

package com.data;

import com.services.P2PAddressI;
import com.services.PositionI;

public class ConnectionInfo {

    private P2PAddressI address;
    private String communicationUnboudPortUri;
    private PositionI initialPosition;
    private double initialRange;
    private String routingInboundPortUri;

    public ConnectionInfo(P2PAddressI address, String communicationUnboudPortUri, PositionI initialPosition, double initialRange, String routingInboundPortUri) {
        this.address = address;
        this.communicationUnboudPortUri = communicationUnboudPortUri;
        this.initialPosition = initialPosition;
        this.initialRange = initialRange;
        this.routingInboundPortUri = routingInboundPortUri;
    }

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

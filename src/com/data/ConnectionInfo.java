package com.data;

import com.services.P2PAddressI;
import com.services.PositionI;

public class ConnectionInfo {

    private P2PAddressI address;
    private String communicationUnboudPortUri;
    private PositionI initialPosition;
    private double initialRange;
    private String routingInboundPortUri;

    public ConnectionInfo(P2PAddressI address,
                          String communicationUnboudPortUri,
                          PositionI initialPosition,
                          double initialRange,
                          String routingInboundPortUri) {
        if(address instanceof P2PAddress){
            this.address = address;
        }
        this.communicationUnboudPortUri = communicationUnboudPortUri;
        if (initialPosition instanceof Position){
            this.initialPosition = initialPosition;
        }
        this.initialRange = initialRange;
        this.routingInboundPortUri = routingInboundPortUri;
    }

    public P2PAddressI getAddress(){
        return this.address;
    }

    public String getCommunicationInboundPortURI(){
        return this.communicationUnboudPortUri;
    }

    public PositionI getInitialPosition() {
        return initialPosition;
    }

    public double getInitialRange() {
        return initialRange;
    }

    public String getRoutingInboundPortURI(){
        return this.routingInboundPortUri;
    }
}

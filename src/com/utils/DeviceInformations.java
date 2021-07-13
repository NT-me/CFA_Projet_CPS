package com.utils;

import com.data.P2PAddress;
import com.data.Position;

public class DeviceInformations {

    private P2PAddress address;
    private String communicationUnboudPortUri;
    private Position initialPosition;
    private double initialRange;
    private String routingInboundPortUri;

    public DeviceInformations(P2PAddress address, String communicationUnboudPortUri, Position initialPosition, double initialRange, String routingInboundPortUri) {
        this.address = address;
        this.communicationUnboudPortUri = communicationUnboudPortUri;
        this.initialPosition = initialPosition;
        this.initialRange = initialRange;
        this.routingInboundPortUri = routingInboundPortUri;
    }

    public P2PAddress getAddress() {
        return address;
    }

    public void setAddress(P2PAddress address) {
        this.address = address;
    }

    public String getCommunicationUnboudPortUri() {
        return communicationUnboudPortUri;
    }

    public void setCommunicationUnboudPortUri(String communicationUnboudPortUri) {
        this.communicationUnboudPortUri = communicationUnboudPortUri;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Position initialPosition) {
        this.initialPosition = initialPosition;
    }

    public double getInitialRange() {
        return initialRange;
    }

    public void setInitialRange(double initialRange) {
        this.initialRange = initialRange;
    }

    public String getRoutingInboundPortUri() {
        return routingInboundPortUri;
    }

    public void setRoutingInboundPortUri(String routingInboundPortUri) {
        this.routingInboundPortUri = routingInboundPortUri;
    }
}

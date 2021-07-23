package com.data;

import com.services.P2PAddressI;
import com.services.PositionI;

/**
 * The type Connection info.
 */
public class ConnectionInfo {

    private P2PAddressI address;
    private String communicationInboudPortUri;
    private PositionI initialPosition;
    private double initialRange;
    private String routingInboundPortUri;

    /**
     * Description: Instantiates a new Connection info.
     * @param address of type P2PAddressI - the address of the concerned component
     * @param communicationInboudPortUri of type String - the URI communication inboud port
     * @param initialPosition of type Position - the initial position of the device
     * @param initialRange of type Double - the initial range of the device
     * @param routingInboundPortUri of type String - the URI routing inbound port
     */
    public ConnectionInfo(P2PAddressI address,
                          String communicationInboudPortUri,
                          PositionI initialPosition,
                          double initialRange,
                          String routingInboundPortUri) {
        if(address instanceof P2PAddress){
            this.address = address;
        }
        this.communicationInboudPortUri = communicationInboudPortUri;
        if (initialPosition instanceof Position){
            this.initialPosition = initialPosition;
        }
        this.initialRange = initialRange;
        this.routingInboundPortUri = routingInboundPortUri;
    }

    /**
     * Description: Get the address of the calling element.
     * @return the address of type P2PAddress
     */
    public P2PAddressI getAddress(){
        return this.address;
    }

    /**
     * Description: Get communication inbound port uri string.
     * @return the communication Inbound Port URI of type String
     */
    public String getCommunicationInboundPortURI(){
        return this.communicationInboudPortUri;
    }

    /**
     * Description: Gets initial position.
     * @return the initial position of type PositionI
     */
    public PositionI getInitialPosition() {
        return initialPosition;
    }

    /**
     * Description: Gets initial range of a device.
     * @return the initial range of type double
     */
    public double getInitialRange() {
        return initialRange;
    }

    /**
     * Get routing inbound port uri string.
     * @return the routing inbound port URI of type String
     */
    public String getRoutingInboundPortURI(){
        return this.routingInboundPortUri;
    }
}
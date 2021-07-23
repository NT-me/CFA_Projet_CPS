package com.services;

import com.data.ConnectionInfo;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface RegistrationCI extends OfferedCI, RequiredCI {


    /**
     * Description : Regsister a P2P device on the simulator
     * @param address of type P2PAddressI - The P2PAddress of the device
     * @param communicationInboundPortURI of type String - The URI of the communication entry port of the device
     * @param initialPositionI of type PositionI - The position inside the simulator of the device
     * @param initialRange of type double - The range of the device
     * @param routingInboundPortURI of type String - the URI of the concerned routing inbound port
     * @return a Set of ConnectionInfo containing informations about the neighbours
     * @throws Exception in case of errors
     */
    Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange,String routingInboundPortURI ) throws Exception;

    /**
     * Description : Register an Access Point on the simulator
     * @param address of type P2PAddressI - The P2PAddress of the device
     * @param communicationInboundPortURI of type String - The URI of the communication entry port of the device
     * @param initialPositionI of type PositionI - The position inside the simulator of the device
     * @param initialRange of type double - The range of the device
     * @param routingInboundPortURI of type String - the URI of the concerned routing inbound port
     * @return a set of type ConnectionInfo - List of information about all
     * @throws Exception in case of error
     */
    Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange,String routingInboundPortURI ) throws Exception;

    //void unregister(P2PAddressI address);
}
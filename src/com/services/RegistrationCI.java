package com.services;

import com.data.ConnectionInfo;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface RegistrationCI extends OfferedCI, RequiredCI {


    /**
     *  Description :
     * @param address of type P2PAddressI - The P2PAddress of the device
     * @param communicationInboundPortURI of type String - The URI of the communication entry port of the device
     * @param initialPositionI of type PositionI - The position inside the simulator of the device
     * @param initialRange of type double - The range of the device
     * @param routingInboundPortURI
     * @return a Set of ConnectionInformations
     * @throws Exception
     */
    public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange,String routingInboundPortURI ) throws Exception;

    /**
     *  Description :
     * @param address of type P2PAddressI - The P2PAddress of the device
     * @param communicationInboundPortURI of type String - The URI of the communication entry port of the device
     * @param initialPositionI of type PositionI - The position inside the simulator of the device
     * @param initialRange of type double - The range of the device
     * @param routingInboundPortURI
     * @return
     * @throws Exception
     */
    public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange,String routingInboundPortURI ) throws Exception;

    //public void unregister(P2PAddressI address);
}

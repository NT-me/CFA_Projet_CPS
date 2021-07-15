package com.services;

import com.data.ConnectionInfo;
import fr.sorbonne_u.components.interfaces.OfferedCI;

import java.util.Set;

public interface RegistrationCI extends OfferedCI {

    public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange,String routingInboudPortURI ) throws Exception;

    public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange,String routingInboudPortURI );

    public void unregister(P2PAddressI address);
}

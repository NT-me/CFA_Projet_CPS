package com.connectors;

import com.data.ConnectionInfo;
import com.services.P2PAddressI;
import com.services.PositionI;
import com.services.RegistrationCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

import java.util.Set;

public class RegistrationConnector extends AbstractConnector implements RegistrationCI {
    @Override
    public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboudPortURI) throws Exception {
        return ((RegistrationCI)this.offering).registerInternal(address, communicationInboundPortURI, initialPositionI, initialRange, routingInboudPortURI);
    }

    @Override
    public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboudPortURI) throws Exception{
        return ((RegistrationCI)this.offering).registerAccessPoint(address,communicationInboundPortURI,initialPositionI,initialRange,routingInboudPortURI);
    }
/*
    @Override
    public void unregister(P2PAddressI address) {

    }

 */
}

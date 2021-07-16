package com.port;

import com.data.ConnectionInfo;
import com.services.P2PAddressI;
import com.services.PositionI;
import com.services.RegistrationCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

import java.util.Set;

public class ParticipantOutboundPort extends AbstractOutboundPort implements RegistrationCI {

    public ParticipantOutboundPort(ComponentI owner) throws Exception{
        super(RegistrationCI.class,owner);
    }

    @Override
    public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboudPortURI) throws Exception {
        return ((RegistrationCI)this.getConnector()).registerInternal(address, communicationInboundPortURI, initialPositionI, initialRange, routingInboudPortURI);
    }

    @Override
    public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboudPortURI) throws Exception{
        return ((RegistrationCI)this.getConnector()).registerInternal(address, communicationInboundPortURI, initialPositionI, initialRange, routingInboudPortURI);
    }
/*
    @Override
    public void unregister(P2PAddressI address) {

    }

 */
}

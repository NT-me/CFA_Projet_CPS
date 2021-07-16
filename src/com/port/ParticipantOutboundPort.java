package com.port;

import com.data.ConnectionInfo;
import com.services.*;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

import java.util.Set;

public class ParticipantOutboundPort extends AbstractOutboundPort implements RegistrationCI, CommunicationCI {

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

    @Override
    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {

    }

    @Override
    public void routeMessage(MessageI m) {

    }

    @Override
    public void ping() {

    }
/*
    @Override
    public void unregister(P2PAddressI address) {

    }

 */
}

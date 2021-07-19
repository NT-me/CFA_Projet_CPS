package com.port;

import com.cfaaato.Participant;
import com.data.ConnectionInfo;
import com.services.*;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

import java.util.Set;

public class ParticipantRegistrationOutboundPort extends AbstractOutboundPort implements RegistrationCI {

    public ParticipantRegistrationOutboundPort(ComponentI owner) throws Exception{
        super(RegistrationCI.class,owner);
    }

    @Override
    public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboundPortURI) throws Exception {
        return ((RegistrationCI)this.getConnector()).registerInternal(
                address,
                communicationInboundPortURI,
                initialPositionI,
                initialRange,
                routingInboundPortURI);
    }

    @Override
    public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboundPortURI) throws Exception{
        return ((RegistrationCI)this.getConnector()).registerInternal(address, communicationInboundPortURI, initialPositionI, initialRange, routingInboundPortURI);
    }

/*
    @Override
    public void routeMessage(MessageIm) {

    }

    @Override
    public void ping() {

    }
    */
/*
    @Override
    public void unregister(P2PAddressI address) {

    }

 */
}

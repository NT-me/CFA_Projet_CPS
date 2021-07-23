package com.port;

import com.data.*;
import com.services.*;
import fr.sorbonne_u.components.*;
import fr.sorbonne_u.components.ports.*;

import java.util.*;

/**
 * The type Participant registration outbound port.
 */
public class ParticipantRegistrationOutboundPort extends AbstractOutboundPort implements RegistrationCI {

    /**
     * Description: Instantiates a new Participant registration outbound port.
     *
     * @param owner of type ComponentI - the owner of that port
     * @throws Exception the exception
     */
    public ParticipantRegistrationOutboundPort(ComponentI owner) throws Exception {
        super(RegistrationCI.class, owner);
    }

    @Override
    public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboundPortURI) throws Exception {
        return ((RegistrationCI) this.getConnector()).registerInternal(
                address,
                communicationInboundPortURI,
                initialPositionI,
                initialRange,
                routingInboundPortURI);
    }

    @Override
    public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboundPortURI) throws Exception {
        return ((RegistrationCI) this.getConnector()).registerInternal(address, communicationInboundPortURI, initialPositionI, initialRange, routingInboundPortURI);
    }
}
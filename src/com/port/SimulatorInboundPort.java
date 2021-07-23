package com.port;

import com.cfaaato.Simulator;
import com.data.ConnectionInfo;
import com.services.P2PAddressI;
import com.services.PositionI;
import com.services.RegistrationCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

import java.util.Set;

/**
 * The type Simulator inbound port.
 */
public class SimulatorInboundPort extends AbstractInboundPort implements RegistrationCI {

    /**
     * The constant URI_REGISTRATION_SIMULATOR_PORT.
     */
    public static final String URI_REGISTRATION_SIMULATOR_PORT = "Porc Originel";

    /**
     * Instantiates a new Simulator inbound port.
     *
     * @param owner the owner
     * @throws Exception the exception
     */
    public SimulatorInboundPort(ComponentI owner) throws Exception {
        super(OfferedCI.class,owner);
    }

    /**
     * Description: Instantiates a new Simulator inbound port.
     * @param uri of type String - the uri of that port
     * @param owner of type ComponentI - the owner of that port
     * @throws Exception the exception
     */
    public SimulatorInboundPort(String uri, Simulator owner) throws Exception {
        super(uri,RegistrationCI.class,owner);
    }

    @Override
    public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboundPortURI) throws Exception{
        ConnectionInfo deviceInfs = new ConnectionInfo(address,communicationInboundPortURI,initialPositionI,initialRange, routingInboundPortURI);
        return this.getOwner().handleRequest( s -> ((Simulator) s ).registerInternal(deviceInfs));
    }

    @Override
    public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboundPortURI) {
        return null;
    }

    /*
    @Override
    public void unregister(P2PAddressI address) {

    }
    */
}
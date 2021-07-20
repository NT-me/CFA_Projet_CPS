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

public class SimulatorInboundPort extends AbstractInboundPort implements RegistrationCI {
    public SimulatorInboundPort(String uri, Class<? extends OfferedCI> implementedInterface, ComponentI owner, String pluginURI, String executorServiceURI) throws Exception {
        super(uri, implementedInterface, owner, pluginURI, executorServiceURI);
    }

    public SimulatorInboundPort(Class<? extends OfferedCI> implementedInterface, ComponentI owner, String pluginURI, String executorServiceURI) throws Exception {
        super(implementedInterface, owner, pluginURI, executorServiceURI);
    }

    public SimulatorInboundPort(String uri, Class<? extends OfferedCI> implementedInterface, ComponentI owner) throws Exception {
        super(uri, implementedInterface, owner);
    }

    public SimulatorInboundPort(Class<? extends OfferedCI> implementedInterface, ComponentI owner) throws Exception {
        super(implementedInterface, owner);
    }public static final String URI_REGISTRATION_SIMULATOR_PORT = "Porc Originel";

    public SimulatorInboundPort(ComponentI owner) throws Exception {
        super(OfferedCI.class,owner);
    }

    public SimulatorInboundPort(String uri, Simulator owner) throws Exception {
        super(uri,RegistrationCI.class,owner);
    }

    @Override
    public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboundPortURI) throws Exception{
        ConnectionInfo deviceInfs = new ConnectionInfo(address,communicationInboundPortURI,initialPositionI,initialRange, routingInboundPortURI);

        return this.getOwner().handleRequest( s -> ((Simulator) s ).registerInternal(deviceInfs));
    }

    @Override
    public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI, PositionI initialPositionI, double initialRange, String routingInboundPortURI) throws Exception {
        ConnectionInfo deviceInfs = new ConnectionInfo(address,communicationInboundPortURI,initialPositionI,initialRange, routingInboundPortURI);
        return this.getOwner().handleRequest( s -> ((Simulator) s ).registerAccessPoint(deviceInfs));
    }

    /*
    @Override
    public void unregister(P2PAddressI address) {

    }
    */
}

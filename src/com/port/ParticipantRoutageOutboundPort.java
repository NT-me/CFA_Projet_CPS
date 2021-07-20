package com.port;

import com.cfaaato.Participant;
import com.data.RouteInfo;
import com.services.P2PAddressI;
import com.services.RoutingManagementCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

import java.util.Set;

public class ParticipantRoutageOutboundPort extends AbstractOutboundPort implements RoutingManagementCI {


    public ParticipantRoutageOutboundPort(String uri, Class<? extends RequiredCI> implementedInterface, ComponentI owner) throws Exception {
        super(uri, implementedInterface, owner);
    }

    public ParticipantRoutageOutboundPort(Class<? extends RequiredCI> implementedInterface, ComponentI owner) throws Exception {
        super(implementedInterface, owner);
    }

    public ParticipantRoutageOutboundPort(ComponentI owner) throws Exception {
        super(RoutingManagementCI.class, owner);
    }

    @Override
    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception{
        this.getOwner().handleRequest(
                p -> {
                    try {
                        ((Participant) p).updateRouting(neighbour, routes);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });
    }


    @Override
    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception{
        this.getOwner().handleRequest(
                p -> {
                    try {
                        ((Participant) p).updateAccessPoint(neighbour, numberOfHops);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });
    }

}

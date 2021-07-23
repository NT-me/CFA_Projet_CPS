package com.port;

import com.cfaaato.*;
import com.data.*;
import com.services.*;
import fr.sorbonne_u.components.*;
import fr.sorbonne_u.components.ports.*;

import java.util.*;

/**
 * The type Participant routage outbound port.
 */
public class ParticipantRoutageOutboundPort extends AbstractOutboundPort implements RoutingManagementCI {
    /**
     * Descrition: Instantiates a new Participant routage outbound port.
     * @param owner of type ComponentI -  the owner of that port
     * @throws Exception the exception
     */
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
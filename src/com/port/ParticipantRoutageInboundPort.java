package com.port;

import com.cfaaato.*;
import com.data.*;
import com.services.*;
import fr.sorbonne_u.components.*;
import fr.sorbonne_u.components.ports.*;

import java.util.*;

/**
 * The type Participant routage inbound port.
 */
public class ParticipantRoutageInboundPort extends AbstractOutboundPort implements RoutingManagementCI {

    /**
     * Description: Instantiates a new Participant routage inbound port.
     * @param uri of type String - the uri of that port
     * @param owner of type ComponentI - the owner of that port
     * @throws Exception the exception
     */
    public ParticipantRoutageInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, RoutingManagementCI.class, owner);
    }

    @Override
    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception {
        this.getOwner().runTask(
                p -> {
                    try {
                        ((Participant) p).updateRouting(neighbour, routes);
                    } catch (Exception e) {
                       // throw new RuntimeException(e);
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception {
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

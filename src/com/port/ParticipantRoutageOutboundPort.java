package com.port;

import com.cfaaato.*;
import com.data.*;
import com.services.*;
import fr.sorbonne_u.components.*;
import fr.sorbonne_u.components.ports.*;

import java.util.*;

public class ParticipantRoutageOutboundPort extends AbstractOutboundPort implements RoutingManagementCI {
    public ParticipantRoutageOutboundPort(ComponentI owner) throws Exception {
        super(RoutingManagementCI.class, owner);
    }

    @Override
    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception{
        ((RoutingManagementCI)this.getConnector()).updateRouting(neighbour, routes);

    }

    @Override
    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception{
        ((RoutingManagementCI)this.getConnector()).updateAccessPoint(neighbour, numberOfHops);
    }

}

package com.connectors;

import com.data.RouteInfo;
import com.services.P2PAddressI;
import com.services.RoutingManagementCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

import java.util.Set;

public class RoutageConnector extends AbstractConnector implements RoutingManagementCI {
    @Override
    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception {
        ((RoutingManagementCI)this.offering).updateRouting(neighbour, routes);
    }

    @Override
    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception {
        ((RoutingManagementCI)this.offering).updateAccessPoint(neighbour, numberOfHops);
    }
}

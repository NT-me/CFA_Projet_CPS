package com.services;

import com.data.RouteInfo;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface RoutingManagementCI extends OfferedCI, RequiredCI{

    /**
     * Description : Update the routing information
     * @param neighbour of type P2PAddressI -
     * @param routes set of routes of type RouteInfo - List of routing information to
     */
    void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes);

    /**
     * Description : Update information about the message
     * @param neighbour of type P2PAddressI - Address of the nearest neighbour
     * @param numberOfHops of type int - Number of hop until
     */
    void updateAccessPoint(P2PAddressI neighbour, int numberOfHops);
}
package com.services;

import com.data.RouteInfo;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface RoutingManagementCI extends OfferedCI, RequiredCI{
    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception;

    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception;
}

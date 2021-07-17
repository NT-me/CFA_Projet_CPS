package com.data;

import com.services.P2PAddressI;
import com.services.RoutingManagementCI;

import com.data.RouteInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RoutingTable implements RoutingManagementCI {

    //Chaque P2PAddressI est un voisin du participant à qui la table de routage appartient. Ce dernier aura plusieurs RouteInfo pour tout les autres participants
    HashMap<P2PAddressI, Set<RouteInfo>> table;

    public RoutingTable() {
        this.table = new HashMap<P2PAddressI, Set<RouteInfo>>();
    }

    public RoutingTable(HashMap<P2PAddressI, Set<RouteInfo>> table) {
        this.table = table;
    }

    public void addNewNeighbor(P2PAddressI neighbour){
        Set<RouteInfo> empty = new HashSet<RouteInfo>();
        this.table.put(neighbour,empty);
    }

    @Override
    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) {

    }

    @Override
    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) {

    }
}


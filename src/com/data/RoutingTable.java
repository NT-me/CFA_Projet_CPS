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

    //Utilisé lorsqu'un Participant se connecte au simulateur, en ajoutant ses voisins, il crée une nouvelle ligne de sa table de routage pour chaque voisin
    public void addNewNeighbor(P2PAddressI neighbour){
        Set<RouteInfo> empty = new HashSet<RouteInfo>();
        this.table.put(neighbour,empty);
    }

    @Override
    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) {
        Set<RouteInfo> actualRoutes = getRoutes(neighbour);
        Set<RouteInfo> neighbourRoutes = routes;
        Set<RouteInfo> newRoutes = new HashSet<RouteInfo>();

        if (!this.table.containsKey(neighbour)){
            this.table.put(neighbour,routes);
        }
        else {
            RouteInfo nRoute = new RouteInfo();
            RouteInfo aRoute = null;
            for (int i = 0;i < neighbourRoutes.size();i++) {
                nRoute = neighbourRoutes.iterator().next();
                RouteInfo routetmp = new RouteInfo();
                for (int j = 0; j < actualRoutes.size(); j++) {
                    routetmp = actualRoutes.iterator().next();
                    if (routetmp.getDestination().equals(nRoute.getDestination())) {
                        aRoute = routetmp;
                    }
                }
            }
                if (aRoute != null){
                    if (aRoute.getNumberOfHops() < nRoute.getNumberOfHops()){
                        newRoutes.add(aRoute);
                    }
                    else{
                        newRoutes.add(nRoute);
                    }
                }
                else{
                    newRoutes.add(nRoute);
                }
        }
        this.table.put(neighbour,newRoutes);
    }

    @Override
    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) {

    }

    public Set<RouteInfo> getRoutes(P2PAddressI address){
        return this.table.get(address);
    }

}


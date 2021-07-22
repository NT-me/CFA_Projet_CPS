package com.data;

import com.services.*;

import java.util.*;

public class RoutingTable {

    //Chaque P2PAddressI est un voisin du participant à qui la table de routage appartient.
    // Ce dernier aura plusieurs RouteInfo pour tout les autres participants.
    private HashMap<P2PAddressI, Set<RouteInfo>> table;

    public RoutingTable() {
        this.table = new HashMap<>();
    }

    public RoutingTable(HashMap<P2PAddressI, Set<RouteInfo>> table) {
        this.table = table;
    }

    public HashMap<P2PAddressI, Set<RouteInfo>> getTable() {
        return table;
    }

    //Utilisé lorsqu'un Participant se connecte au simulateur, en ajoutant ses voisins, il crée une nouvelle ligne de sa table de routage pour chaque voisin
    public void addNewNeighbor(P2PAddressI neighbour){
        Set<RouteInfo> empty = new HashSet<>();
        this.table.put(neighbour,empty);
    }

    public Set<RouteInfo> getRoutes(P2PAddressI address){
        return this.table.get(address);
    }

}
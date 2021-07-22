package com.data;
import com.services.*;
import java.util.*;
import com.services.P2PAddressI;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

    public void addNeighbors(P2PAddressI myAdress, Set<ConnectionInfo> neighbors){

        for (ConnectionInfo coi : neighbors) {
            Set<RouteInfo> tr = new HashSet<RouteInfo>();
            tr.add(new RouteInfo(myAdress,1));
            this.table.put(coi.getAddress(), tr);
        }
    }

    public Set<RouteInfo> getRoutes(P2PAddressI address){
        return this.table.get(address);
    }


    public Collection<Set<RouteInfo>> getKeys(){
        return this.table.values();
    }

    public HashMap<P2PAddressI, Set<RouteInfo>> getTable() {
        return this.table;
    }
}


package com.data;
import com.services.*;

import java.util.*;

/**
 * The type Routing table.
 */
public class RoutingTable {

    //Chaque P2PAddressI est un voisin du participant à qui la table de routage appartient.
    // Ce dernier aura plusieurs RouteInfo pour tout les autres participants.
    private HashMap<P2PAddressI, Set<RouteInfo>> table;

    /**
     * Description: Instantiates a new Routing table.
     */
    public RoutingTable() {
        this.table = new HashMap<>();
    }

    /**
     * Description: return the table.
     * @return the table
     */
    public HashMap<P2PAddressI, Set<RouteInfo>> getTable() {
        return table;
    }

    /**
     * Description: Add new neighbor.
     * @param neighbour of type P2PAddressI - the adress of the neighbor to add
     */
    //Utilisé lorsqu'un Participant se connecte au simulateur, en ajoutant ses voisins, il crée une nouvelle ligne de sa table de routage pour chaque voisin
    public void addNewNeighbor(P2PAddressI neighbour){
        Set<RouteInfo> empty = new HashSet<>();
        this.table.put(neighbour,empty);
    }

    /**
     * Description: Add neighbors.
     * @param myAdress of type P2PAddressI - the adress of the concerned
     * @param neighbors the neighbors
     */
    public void addNeighbors(P2PAddressI myAdress, Set<ConnectionInfo> neighbors){

        for (ConnectionInfo coi : neighbors) {
            Set<RouteInfo> tr = new HashSet<RouteInfo>();
            tr.add(new RouteInfo(myAdress,1));
            this.table.put(coi.getAddress(), tr);
        }
    }

    public void addNewAddressRoutes(P2PAddressI myAdress, Set<RouteInfo> routes){
            this.table.put(myAdress, routes);
    }

    public void updateSetRoute(P2PAddressI myAdress, RouteInfo route){
        RouteInfo modifiedRoute = route;
        modifiedRoute.setNumberOfHops(modifiedRoute.getNumberOfHops() + 1);
        this.table.get(myAdress).add(modifiedRoute);
    }

    public void updateNewRoute(P2PAddressI myAdress, RouteInfo route){
        this.table.get(myAdress).add(route);
    }

    /**
     * Descrition: Get routes set.
     * @param address of type P2PAddressI - the address of the concerned participant
     * @return a set of type RouteInfo
     */
    public Set<RouteInfo> getRoutes(P2PAddressI address){
        return this.table.get(address);
    }


    /**
     * Description: Get keys collection.
     * @return a collection of type Set<RouteInfo>
     */
    public Collection<Set<RouteInfo>> getKeys(){
        return this.table.values();
    }

}
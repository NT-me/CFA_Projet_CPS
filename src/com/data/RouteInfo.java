package com.data;
import com.services.AddressI;

/**
 * The type Route info.
 */
public class RouteInfo {

    private AddressI Destination;
    private int numberOfHops;

    /**
     * Description: Instantiates a new Route info.
     * @param destination  the destination
     * @param numberOfHops the number of hops
     */
    public RouteInfo(AddressI destination, int numberOfHops) {
        Destination = destination;
        this.numberOfHops = numberOfHops;
    }

    /**
     * Description: Gets destination.
     * @return the destination
     */
    public AddressI getDestination() {
        return Destination;
    }

    /**
     * Descripton: Gets number of hops.
     * @return the number of hops
     */
    public int getNumberOfHops() {
        return numberOfHops;
    }
}
package com.data;
import com.services.AddressI;
public class RouteInfo {

    private AddressI Destination;
    private int numberOfHops;

    public RouteInfo(AddressI destination, int numberOfHops) {
        Destination = destination;
        this.numberOfHops = numberOfHops;
    }

    public AddressI getDestination() {
        return Destination;
    }

    public void setDestination(AddressI destination) {
        Destination = destination;
    }

    public int getNumberOfHops() {
        return numberOfHops;
    }

    public void setNumberOfHops(int numberOfHops) {
        this.numberOfHops = numberOfHops;
    }
}

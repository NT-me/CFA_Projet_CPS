package com.services;

public interface PositionI {

    /**
     * Description : Compute distance between the calling element and the argument
     * @param other of type PositionI - Position of another
     * @return distance of type double
     */
    double distance(PositionI other);

}
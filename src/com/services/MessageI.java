package com.services;

import java.io.Serializable;

public interface MessageI {

    /**
     * Description : Returns the address of the aimed device
     * @return address of type AddressI - the address of the
     */
    public AddressI getAddress();

    /**
     * Description : Returns the content of the message
     * @return the message of type Serializable
     */
    public Serializable getContent();

    /**
     * Description :
     * @return value of type boolean - True if message is still alive otherwise False
     */
    public boolean stillAlive();

    /**
     * Description : Decrements by one the number of hops of that message
     */
    public void decrementHops();
}

package com.services;

import java.io.Serializable;

public interface MessageI {
    public AddressI getAddress();

    public Serializable getContent();

    public boolean stillAlive();

    public void decrementHops();
}

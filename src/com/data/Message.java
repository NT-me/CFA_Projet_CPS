package com.data;

import com.services.AddressI;
import com.services.MessageI;
import com.utils.ConstantsValues;

import java.io.Serializable;

public class Message implements MessageI {

    private AddressI adress;
    private Serializable content;
    private boolean stillAlive;
    private int hops;

    public Message(AddressI adress) {
        this.adress = adress;
        this.content = "Empty";
        this.stillAlive = true;
        this.hops = ConstantsValues.MAX_HOPS;
    }

    public Message(AddressI adress, Serializable content, boolean stillAlive, int hops) {
        this.adress = adress;
        this.content = content;
        this.stillAlive = stillAlive;
        this.hops = hops;
    }

    @Override
    public AddressI getAddress() {
        return this.adress;
    }

    @Override
    public Serializable getContent() {
        return content;
    }

    @Override
    public boolean stillAlive() {
        return stillAlive;
    }

    @Override
    public void decrementHops() {
        this.hops--;
    }
}

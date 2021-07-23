package com.data;

import com.services.AddressI;
import com.services.MessageI;
import com.utils.ConstantsValues;

import java.io.Serializable;

/**
 * The type Message.
 */
public class Message implements MessageI {

    private AddressI adress;
    private Serializable content;
    private boolean stillAlive;
    private int hops;

    /**
     * Description: Instantiates a new Message.
     * @param adress of type AddressI - the address to transmit to
     */
    public Message(AddressI adress) {
        this.adress = adress;
        this.content = "Full";
        this.stillAlive = true;
        this.hops = ConstantsValues.MAX_HOPS;
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
        if(this.hops<=0){
            this.stillAlive=false;
        }
    }
}
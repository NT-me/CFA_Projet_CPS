package com.data;

import com.services.AddressI;
import com.services.P2PAddressI;

import java.util.Random;

public class P2PAddress implements P2PAddressI {
    private String value;
    private boolean isP2PAddress;
    private boolean isIPAddress;

    private String fakeAdresse(){
        Random r = new Random();
        return hashCode() + "-" + r.nextInt(256) + "" + r.nextInt(256) + ":" + r.nextInt(256) + "" + r.nextInt(256) + ":" + r.nextInt(256) + "" + r.nextInt(256);
    }

/*    public P2PAddress(boolean isP2PAddress, boolean isIPAddress) {
        this.isP2PAddress = isP2PAddress;
        this.isIPAddress = isIPAddress;
    }*/

    public P2PAddress(){
        this.value = this.fakeAdresse();
        this.isIPAddress = false;
        this.isP2PAddress = true;
    }

    @Override
    public boolean isP2PAddress() {
        return isP2PAddress;
    }

    @Override
    public boolean isIPAddress() {
        return isIPAddress;
    }

    @Override
    public boolean equals(AddressI a) {
        return a.hashCode() == this.hashCode();
    }

    @Override
    public String toString(){
        return this.value;
    }
}

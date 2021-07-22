package com.data;

import com.services.AddressI;
import com.services.IPAddressI;

import java.util.Random;

public class IPAddress implements IPAddressI {
    private String value;
    private boolean isP2PAddress;
    private boolean isIPAdress;

    private String fakeIP(){
        Random r = new Random();
        return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }

    public IPAddress(){
        this.value = this.fakeIP();
        this.isP2PAddress = false;
        this.isIPAdress = true;
    }

    @Override
    public boolean equals(AddressI a) {
        return false;
    }

    @Override
    public boolean isP2PAddress() {
        return false;
    }

    @Override
    public boolean isIPAddress() {
        return false;
    }

    @Override
    public String toString(){
        return this.value;
    }
}

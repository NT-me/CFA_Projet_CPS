package com.data;

import com.services.AddressI;
import com.services.P2PAddressI;

/**
 * The type P 2 p address.
 */
public class P2PAddress implements P2PAddressI {
    /**
     * The Is p 2 p address.
     */
    boolean isP2PAddress;
    /**
     * The Is ip address.
     */
    boolean isIPAddress;

    /**
     * Descripton: Instantiates a new P2PAddress.
     */
    public P2PAddress(){
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
}
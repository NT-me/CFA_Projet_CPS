package com.data;

import com.services.P2PAddressI;

public class P2PAddress implements P2PAddressI {
    boolean isP2PAddress;
    boolean isIPAddress;

    public P2PAddress(boolean isP2PAddress, boolean isIPAddress) {
        this.isP2PAddress = isP2PAddress;
        this.isIPAddress = isIPAddress;
    }

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
}

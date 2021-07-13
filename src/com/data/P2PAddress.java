package com.data;

import com.services.P2PAddressI;

public class P2PAddress implements P2PAddressI {
    public boolean isP2PAddress;
    public boolean isIPAddress;


    @Override
    public boolean isP2PAddress() {
        return isP2PAddress;
    }

    @Override
    public boolean isIPAddress() {
        return isIPAddress;
    }
}

package com.services;

public interface AddressI extends P2PAddressI,IPAddressI{

    boolean isP2PAddress();

    boolean isIPAddress();

    boolean equals(AddressI a);

}

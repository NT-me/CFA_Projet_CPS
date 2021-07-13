package com.services;

public interface AddressI extends P2PAddressI,IPAddressI{

    public boolean isP2PAddress();

    public boolean isIPAddress();

    public boolean equals(AddressI a);

}

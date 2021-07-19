package com.services;

public interface AddressI{

    boolean isP2PAddress();

    boolean isIPAddress();

    boolean equals(AddressI a);

}

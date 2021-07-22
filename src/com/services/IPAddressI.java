package com.services;

public interface IPAddressI extends AddressI {

    /**
     * Description : Ensure that this address is a P2PAddress
     * @return true if P2PAddress, otherwise false
     */
    default boolean isP2PAddress() {
        return false;
    }

    /**
     * Description : Ensure that this address is a IPAddress
     * @return true if IPAddress, otherwise false
     */
    default boolean isIPAddress() {
        return true;
    }
}

package com.services;

public interface P2PAddressI extends AddressI {

    /**
     * Description : Ensure that this address is a P2PAddress
     * @return true if P2PAddress, otherwise false
     */
    default boolean isP2PAddress() {
        return true;
    }
    /**
     * Description : Ensure that this address is a IPPAddress
     * @return true if IPAddress, otherwise false
     */
    default boolean isIPAddress() {
        return false;
    }

}

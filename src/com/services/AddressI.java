package com.services;

public interface AddressI{

    /**
     * Description : Ensure that the calling element is a P2PAddress
     * @return true if it's a P2PAddress, otherwise returns false
     */
    boolean isP2PAddress();

    /**
     * Description : Ensure that the calling element is a IPAddress
     * @return true if it's a IPAddress, otherwise returns false
     */
    boolean isIPAddress();

    /**
     * Description : Ensure that an AddressI element is equals to another one
     * @param a of type AddressI - The AddressI to compare to
     * @return true if it's equals, otherwise returns false
     */
    boolean equals(AddressI a);

}

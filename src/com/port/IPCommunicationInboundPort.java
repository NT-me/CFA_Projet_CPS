package com.port;

import com.cfaaato.AccessPoint;
import com.cfaaato.IPDevice;
import com.services.IPAddressI;
import com.services.IPCommunicationCI;
import com.services.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class IPCommunicationInboundPort extends AbstractInboundPort implements IPCommunicationCI {


    public IPCommunicationInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, IPCommunicationCI.class, owner);
    }

    @Override
    public void connectToServer(IPAddressI address, String communicationPortURI) throws Exception {
        this.getOwner().handleRequest(
                d -> {
                    try {
                        ((AccessPoint) d).connectToServer(address, communicationPortURI);
                    } catch (Exception e){
                        throw new RuntimeException(e);
                    }
                    return null;
                }
        );
    }

    @Override
    public void routeMessage(MessageI m) throws Exception {

    }
}

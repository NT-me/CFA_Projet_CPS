package com.port;

import com.cfaaato.*;
import com.services.*;
import fr.sorbonne_u.components.*;
import fr.sorbonne_u.components.ports.*;

/**
 * The type Participant communication inbound port.
 */
public class ParticipantCommunicationInboundPort extends AbstractOutboundPort implements CommunicationCI {

    /**
     * Description : Instantiates a new Participant communication inbound port.
     * @param uri of type String - the uri of the concerned port
     * @param owner of type ComponentI - the owner of that port
     * @throws Exception the exception
     */
    public ParticipantCommunicationInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, CommunicationCI.class, owner);
    }

    @Override
    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {
        this.getOwner().runTask(
            p->{
                try {
                    ((Participant) p).connect(address, communicationInboundPortURI, routingInboundPortURI);
                } catch (Exception e) {
                //         throw new RuntimeException(e);
                e.printStackTrace();
                }
        });
    }

    @Override
    public void routeMessage(MessageI m) throws Exception {
        this.getOwner().handleRequest(
                p -> {
                    try {
                        ((Participant) p).routeMessage(m);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });
    }
    /*
    @Override
    public void ping() {

    }
*/
}
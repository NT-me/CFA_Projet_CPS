package com.port;

import com.cfaaato.*;
import com.services.*;
import fr.sorbonne_u.components.*;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.ports.*;

public class ParticipantCommunicationInboundPort extends AbstractInboundPort implements CommunicationCI {

    public ParticipantCommunicationInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, CommunicationCI.class, owner);
    }

    public ParticipantCommunicationInboundPort(String uri, ComponentI owner, String executorServiceURI) throws Exception {
        super(uri, CommunicationCI.class, owner, null, executorServiceURI);
    }

    @Override
    public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception {
        this.getOwner().runTask(getExecutorServiceIndex(),
            p->{
                try {
                    ((Participant) p).connect(address, communicationInboundPortURI, routingInboundPortURI);
                } catch (Exception e) {
                //         throw new RuntimeException(e);
                e.printStackTrace();
                }
        });
/*         this.getOwner().handleRequest(
                 p -> {
                     try {
                         ((Participant) p).connect(address, communicationInboundPortURI, routingInboundPortURI);

                     } catch (Exception e) {
                         throw new RuntimeException(e);
                     }
                     return null;
                 });*/
    }

    @Override
    public void routeMessage(MessageI m) throws Exception {
        this.getOwner().runTask(getExecutorServiceIndex(),
                p -> {
                    try {
                        ((Participant) p).routeMessage(m);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    /*return null;*/
                });
    }
    /*
    @Override
    public void ping() {

    }
*/
}
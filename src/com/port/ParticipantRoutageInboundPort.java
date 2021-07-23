package com.port;

import com.cfaaato.Participant;
import com.data.RouteInfo;
import com.services.P2PAddressI;
import com.services.RoutingManagementCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;


import java.util.Set;

public class ParticipantRoutageInboundPort extends AbstractInboundPort implements RoutingManagementCI {

    public ParticipantRoutageInboundPort(String uri, Class<? extends OfferedCI> implementedInterface, ComponentI owner) throws Exception {
        super(uri, implementedInterface, owner);
    }

    public ParticipantRoutageInboundPort(String uri, ComponentI owner) throws Exception {
        super(uri, RoutingManagementCI.class, owner);
    }

    public ParticipantRoutageInboundPort(Class<? extends OfferedCI> implementedInterface, ComponentI owner) throws Exception {
        super(implementedInterface, owner);
    }

    @Override
    public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception{
        this.getOwner().runTask(
                p -> {
                    try {
                        ((Participant) p).updateRouting(neighbour, routes);

                    } catch (Exception e) {
                       // throw new RuntimeException(e);
                        e.printStackTrace();
                    }
                });
    }
//     this.getOwner().runTask(
//            p->{
//        try {
//            ((Participant) p).connect(address, communicationInboundPortURI, routingInboundPortURI);
//        } catch (Exception e) {
//            //         throw new RuntimeException(e);
//            e.printStackTrace();
//        }
//    });

    @Override
    public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception{
        this.getOwner().handleRequest(
                p -> {
                    try {
                        ((Participant) p).updateAccessPoint(neighbour, numberOfHops);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });
    }
}

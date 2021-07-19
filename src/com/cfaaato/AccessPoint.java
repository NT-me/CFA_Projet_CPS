package com.cfaaato;

import com.data.ConnectionInfo;
import com.port.ParticipantCommunicationInboundPort;
import com.port.ParticipantCommunicationOutboundPort;
import com.port.ParticipantRegistrationOutboundPort;
import fr.sorbonne_u.components.AbstractComponent;

import java.util.Set;

public class AccessPoint extends AbstractComponent {

    protected ParticipantRegistrationOutboundPort proap;
    protected ParticipantCommunicationOutboundPort pcoap;
    protected ParticipantCommunicationInboundPort pciap;
    private ConnectionInfo myInformations;
    private Set<ConnectionInfo> neighbors;

    protected AccessPoint(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
    }

    protected AccessPoint(String reflectionInboundPortURI, int nbThreads, int nbSchedulableThreads) {
        super(reflectionInboundPortURI, nbThreads, nbSchedulableThreads);
    }
}

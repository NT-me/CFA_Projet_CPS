package com.cfaaato;

import com.connectors.RegistrationConnector;
import com.data.ConnectionInfo;
import com.port.ParticipantOutboundPort;
import com.services.ParticipantCI;
import com.services.RegistrationCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;

import java.util.Set;

@RequiredInterfaces(required={RegistrationCI.class})
public class Participant extends AbstractComponent {

    protected ParticipantOutboundPort pop;
    private ConnectionInfo myInformations;
    private Set<ConnectionInfo> neighbors;

    protected Participant(int nbThreads, int nbSchedulableThreads) throws Exception {
        super(nbThreads, nbSchedulableThreads);
    }

    protected Participant(String reflectionInboundPortURI, int nbThreads, int nbSchedulableThreads) throws Exception {
        super(reflectionInboundPortURI, nbThreads, nbSchedulableThreads);
    }

    @Override
    public void start(){
        try {
            this.pop = new ParticipantOutboundPort(this);   //creation du port
            this.pop.publishPort();     //publication du port
            this.doPortConnection(this.pop.getPortURI(),Main.URI_REGISTRATION_SIMULATOR_PORT, RegistrationConnector.class.getCanonicalName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

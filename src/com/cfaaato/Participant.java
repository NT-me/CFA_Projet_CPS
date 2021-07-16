package com.cfaaato;

import com.connectors.RegistrationConnector;
import com.data.ConnectionInfo;
import com.data.P2PAddress;
import com.data.Position;
import com.port.ParticipantOutboundPort;
import com.services.PositionI;
import com.services.RegistrationCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

import java.util.HashSet;
import java.util.Set;

@RequiredInterfaces(required={RegistrationCI.class})
public class Participant extends AbstractComponent {

    protected ParticipantOutboundPort pop;
    private ConnectionInfo myInformations;
    private Set<ConnectionInfo> neighbors;

    protected Participant(int nbThreads, int nbSchedulableThreads) throws Exception {
        super(nbThreads, nbSchedulableThreads);
        this.neighbors = new HashSet<ConnectionInfo>();
    }

    protected Participant(String reflectionInboundPortURI, int nbThreads, int nbSchedulableThreads) throws Exception {
        super(reflectionInboundPortURI, nbThreads, nbSchedulableThreads);
    }

    @Override
    public void start() throws ComponentStartException {
        super.start();
    }

    @Override
    public void execute() throws Exception {
        super.execute();
        try {
            this.pop = new ParticipantOutboundPort(this);   //creation du port
            this.pop.publishPort();     //publication du port
            this.doPortConnection(this.pop.getPortURI(), CVM.URI_REGISTRATION_SIMULATOR_PORT, RegistrationConnector.class.getCanonicalName());

            P2PAddress P2PAdress_init = new P2PAddress();
            Position pos = new Position(3, 4);
            ConnectionInfo myInfo_init = new ConnectionInfo(P2PAdress_init,
                    this.pop.getPortURI(),
                    pos,
                    0,
                    "0");
            this.myInformations = myInfo_init;

            this.neighbors = this.pop.registerInternal(
                    this.myInformations.getAddress(),
                    this.myInformations.getCommunicationInboundPortURI(),
                    this.myInformations.getInitialPosition(),
                    this.myInformations.getInitialRange(),
                    this.myInformations.getRoutingInboundPortURI()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void			finalise() throws Exception
    {
        this.doPortDisconnection(this.pop.getPortURI());
        this.pop.unpublishPort();

        super.finalise();
    }
}

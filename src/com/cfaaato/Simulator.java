package com.cfaaato;

import com.data.ConnectionInfo;
import com.port.SimulatorInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

import java.util.HashSet;
import java.util.Set;

public class Simulator extends AbstractComponent {

    private Set<ConnectionInfo> listDevicesInformation;
    private SimulatorInboundPort sip;

    protected Simulator(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
        this.listDevicesInformation = new HashSet<ConnectionInfo>();
    }

    public Set<ConnectionInfo> registerInternal(ConnectionInfo deviceInf) throws Exception{
        //TODO return a filtered list of devicesInformations
        Set<ConnectionInfo> ret_Set = new HashSet<>(this.listDevicesInformation);
        listDevicesInformation.add(deviceInf);
        return ret_Set;
    }

    @Override
    public void start() throws ComponentStartException {
        super.start();
        try {
            this.sip = new SimulatorInboundPort(CVM.URI_REGISTRATION_SIMULATOR_PORT,this);
            this.sip.publishPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

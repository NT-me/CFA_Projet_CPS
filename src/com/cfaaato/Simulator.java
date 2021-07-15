package com.cfaaato;

import com.data.ConnectionInfo;
import com.port.SimulatorInboundPort;
import com.utils.DeviceInformations;
import fr.sorbonne_u.components.AbstractComponent;

import java.util.List;
import java.util.Set;

public class Simulator extends AbstractComponent {

    private Set<ConnectionInfo> listDevicesInformation;
    private SimulatorInboundPort sip;

    protected Simulator(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
    }

    public Set<ConnectionInfo> registerInternal(ConnectionInfo deviceInf) throws Exception{
        //TODO return a filtered list of devicesInformations
        listDevicesInformation.add(deviceInf);
        return listDevicesInformation;
    }

    @Override
    public void start(){
        try {
            this.sip = new SimulatorInboundPort(Main.URI_REGISTRATION_SIMULATOR_PORT,this);
            this.sip.publishPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

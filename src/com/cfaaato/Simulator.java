package com.cfaaato;

import com.data.ConnectionInfo;
import com.port.SimulatorInboundPort;
import com.utils.ConstantsValues;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

import java.util.HashSet;
import java.util.Set;

public class Simulator extends AbstractComponent {

    private Set<ConnectionInfo> listDevicesInformation;
    private Set<ConnectionInfo> listAccessPointInformation;
    private SimulatorInboundPort sip;

    protected Simulator(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
        this.listDevicesInformation = new HashSet<ConnectionInfo>();
    }

    public Set<ConnectionInfo> registerInternal(ConnectionInfo deviceInf) throws Exception{
        Set<ConnectionInfo> ret_Set = new HashSet<>(this.listDevicesInformation);
        Set<ConnectionInfo> filteredSet = new HashSet<>();
        listDevicesInformation.add(deviceInf);

        for (ConnectionInfo coi : ret_Set){
            if (coi.getInitialPosition().distance(deviceInf.getInitialPosition()) < deviceInf.getInitialRange()){
                filteredSet.add(coi);
            }
        }
        return filteredSet;
    }

    public Set<ConnectionInfo> registerAccessPoint(ConnectionInfo deviceInf) throws Exception {
        Set<ConnectionInfo> ret_Set = new HashSet<>(this.listDevicesInformation);
        Set<ConnectionInfo> filteredSet = new HashSet<>();
        listAccessPointInformation.add(deviceInf);

        for (ConnectionInfo coi : ret_Set){
            if (coi.getInitialPosition().distance(deviceInf.getInitialPosition()) < deviceInf.getInitialRange()){
                filteredSet.add(coi);
            }
        }
        return filteredSet;
    }

    @Override
    public void start() throws ComponentStartException {
        super.start();
        try {
            this.sip = new SimulatorInboundPort(ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT,this);
            this.sip.publishPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

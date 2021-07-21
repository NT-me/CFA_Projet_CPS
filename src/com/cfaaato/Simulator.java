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
        this.listAccessPointInformation = new HashSet<>();
    }

    public Set<ConnectionInfo> registerInternal(ConnectionInfo deviceInf) throws Exception{
        Set<ConnectionInfo> ret_Set = new HashSet<>(this.listDevicesInformation);
        Set<ConnectionInfo> filteredSet = new HashSet<>();
        this.listDevicesInformation.add(deviceInf);

        for (ConnectionInfo coi : ret_Set){
            if (coi.getInitialPosition().distance(deviceInf.getInitialPosition()) < deviceInf.getInitialRange()){
                filteredSet.add(coi);
            }
        }

        for (ConnectionInfo lpi : this.listAccessPointInformation){
            if (lpi.getInitialPosition().distance(deviceInf.getInitialPosition()) < deviceInf.getInitialRange()){
                filteredSet.add(lpi);
            }
        }

        return filteredSet;
    }

    public Set<ConnectionInfo> registerAccessPoint(ConnectionInfo deviceInf) throws Exception {
        Set<ConnectionInfo> ret_Set = new HashSet<>(this.listAccessPointInformation);
        Set<ConnectionInfo> filteredSet = new HashSet<>();
        listAccessPointInformation.add(deviceInf);

        // Ajouter les participants du réseau P2P suffisament proche
        for (ConnectionInfo coi : this.listDevicesInformation){
            if (coi.getInitialPosition().distance(deviceInf.getInitialPosition()) < deviceInf.getInitialRange()){
                filteredSet.add(coi);
            }
        }

        // Ajouter tous les accessPoints
        filteredSet.addAll(ret_Set);
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

    @Override
    public void finalise() throws Exception {
        System.out.println("Participants list : " + this.listDevicesInformation);
        System.out.println("AP list : " + this.listAccessPointInformation);
        this.sip.unpublishPort();
    }
}

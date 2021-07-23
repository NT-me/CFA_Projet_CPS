package com.cfaaato;

import com.data.ConnectionInfo;
import com.port.SimulatorInboundPort;
import com.utils.ConstantsValues;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Simulator.
 */
public class Simulator extends AbstractComponent {

    private Set<ConnectionInfo> listDevicesInformation;
    private SimulatorInboundPort sip;

    /**
     * Description: Instantiates a new Simulator.
     * @param nbThreads of type int - the number of threads to use
     * @param nbSchedulableThreads of type int - the number of schedulable threads
     */
    protected Simulator(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
        this.listDevicesInformation = new HashSet<ConnectionInfo>();
        try {
            this.sip = new SimulatorInboundPort(ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT,this);
            this.sip.publishPort();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description : Register internal set.
     * @param deviceInf of type ConnectionInfo - the device informations
     * @return a set of connection of type ConnectionInfo
     * @throws Exception the exception
     */
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

    @Override
    public void start() throws ComponentStartException {
        super.start();
        try {
            // this.sip = new SimulatorInboundPort(ConstantsValues.URI_REGISTRATION_SIMULATOR_PORT,this);
            // this.sip.publishPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
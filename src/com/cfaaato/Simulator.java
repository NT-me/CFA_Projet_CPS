package com.cfaaato;

import com.data.ConnectionInfo;
import com.utils.DeviceInformations;
import fr.sorbonne_u.components.AbstractComponent;

import java.util.List;
import java.util.Set;

public class Simulator extends AbstractComponent {

    private Set<ConnectionInfo> listDevicesInformation;

    protected Simulator(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
    }

    public Set<ConnectionInfo> registerInternal(ConnectionInfo deviceInf) throws Exception{
        //TODO return a filtered list of devicesInformations
        listDevicesInformation.add(deviceInf);
        return listDevicesInformation;
    }
}

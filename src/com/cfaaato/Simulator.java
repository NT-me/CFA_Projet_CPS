package com.cfaaato;

import com.utils.DeviceInformations;
import fr.sorbonne_u.components.AbstractComponent;

import java.util.List;

public class Simulator extends AbstractComponent {

    private List<DeviceInformations> listDevicesInformation;

    protected Simulator(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
    }

    protected void registerInternal(DeviceInformations deviceInf){

    }
}

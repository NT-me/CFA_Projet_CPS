package com.cfaaato;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.examples.basic_cs.CVM;

public class Main extends AbstractCVM {

    public static final String URI_REGISTRATION_SIMULATOR_PORT = "Porc Originel";

    public Main() throws Exception {
    }

    @Override
    public void deploy() throws Exception{
        AbstractComponent.createComponent(Participant.class.getCanonicalName(), new Object[]{});
        AbstractComponent.createComponent(Simulator.class.getCanonicalName(), new Object[]{});

        super.deploy();
    }
    public static void main(String[] args) {
	    try {
	        CVM cvm = new CVM();
	        cvm.startStandardLifeCycle(1000L);
	        Thread.sleep(1000L);
	        System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

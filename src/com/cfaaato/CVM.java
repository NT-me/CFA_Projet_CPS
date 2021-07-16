package com.cfaaato;

import com.data.Position;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import java.util.Random;

public class CVM extends AbstractCVM {

    public CVM() throws Exception {
        super();
    }

    @Override
    public void deploy() throws Exception{
        Random rd = new Random(); // creating Random object
        rd.nextInt();
        AbstractComponent.createComponent(
                Simulator.class.getCanonicalName(), new Object[]{1,0}
        );

        AbstractComponent.createComponent(
                Participant.class.getCanonicalName(), new Object[]{1,0, new Position(rd.nextInt(5), rd.nextInt(5))}
        );

        AbstractComponent.createComponent(
                Participant.class.getCanonicalName(), new Object[]{1,0, new Position(rd.nextInt(5), rd.nextInt(5))}
        );

        AbstractComponent.createComponent(
                Participant.class.getCanonicalName(), new Object[]{1,0, new Position(rd.nextInt(5), rd.nextInt(5))}
        );
        super.deploy();
    }
    public static void main(String[] args) {
	    try {
	        //fr.sorbonne_u.components.examples.basic_cs.CVM cvm = new fr.sorbonne_u.components.examples.basic_cs.CVM();
	        CVM cvm = new CVM();
            cvm.startStandardLifeCycle(1000L);
	        Thread.sleep(2000L);
	        System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

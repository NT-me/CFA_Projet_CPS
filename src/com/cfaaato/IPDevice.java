package com.cfaaato;

import com.connectors.IPCommunicationConnector;
import com.data.IPAddress;
import com.port.IPCommunicationInboundPort;
import com.port.IPCommunicationOutboundPort;
import com.services.IPAddressI;
import com.services.IPCommunicationCI;
import com.services.MessageI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;

import java.util.UUID;

@RequiredInterfaces(required={IPCommunicationCI.class})
@OfferedInterfaces(offered = {IPCommunicationCI.class})
public class IPDevice extends AbstractComponent {
    private IPAddress address;
    private String serverPortURI;
    protected IPCommunicationInboundPort ipcip;
    protected IPCommunicationOutboundPort ipcop;


    protected IPDevice(int nbThreads, int nbSchedulableThreads, String serverPortURI) throws Exception {
        super(nbThreads, nbSchedulableThreads);
        this.address = new IPAddress();
        this.serverPortURI = serverPortURI;

        this.ipcip = new IPCommunicationInboundPort(serverPortURI, this);
        this.ipcop = new IPCommunicationOutboundPort(UUID.randomUUID().toString(), this);
    }

    public void routeMessage(MessageI m) throws Exception{
        if (m != null){
            if (m.getAddress().equals(this.address)){
                System.out.println(
                        this.address.toString()
                                + " | Msg name : "
                                + m.hashCode()
                                + " | Msg send to : "
                                + m.getAddress()
                );
            }
        }
    }

    @Override
    public void start(){
        try {
            //ipcop.doConnection(this.serverPortURI, IPCommunicationCI.class.getCanonicalName());
            this.doPortConnection(
                    this.ipcop.getPortURI(),
                    this.serverPortURI,
                    IPCommunicationConnector.class.getCanonicalName()
                    );
            ipcop.connectToServer(
                    this.address,
                    this.ipcip.getPortURI()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

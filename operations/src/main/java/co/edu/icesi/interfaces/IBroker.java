package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Broker
 */
public interface IBroker extends Remote {

    public void register(String protocol, String ip, int port, String service) throws IllegalArgumentException;
    
    public List<String> getServicesIP(String service) throws IllegalArgumentException;

}
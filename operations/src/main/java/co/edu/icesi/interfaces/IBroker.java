package co.edu.icesi.interfaces;


/**
 * Broker
 */
public interface IBroker {

    public void register(String ip, int port, String service) throws IllegalArgumentException;
    
    public String getMultiplicationService() throws IllegalArgumentException;
    
    public void notifyByService(String ip);

}
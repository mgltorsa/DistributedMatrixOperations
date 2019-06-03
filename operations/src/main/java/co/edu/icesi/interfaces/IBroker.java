<<<<<<< HEAD
package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.util.List;

/**
 * Broker
 */
public interface IBroker extends Remote {

    public void register(String protocol, String ip, int port, String service) throws IllegalArgumentException;
    
    public List<String> getServicesIP(String service) throws IllegalArgumentException;

=======
package co.edu.icesi.interfaces;


/**
 * Broker
 */
public interface IBroker {

    public void register(String ip, int port, String service) throws IllegalArgumentException;

    public String[] getTiffProcessors(int quantity) throws IllegalArgumentException;
    
    public int getTotalProcessors();

>>>>>>> d4755263fc315e725c44e61eecea6eef0e37914e
}
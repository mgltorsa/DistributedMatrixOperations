package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.util.List;

public interface IBroker extends Remote{

	public void register(String protocol, String ip, int port, String service) throws IllegalArgumentException;

	public List<String> getServicesIP(String service) throws IllegalArgumentException;
}

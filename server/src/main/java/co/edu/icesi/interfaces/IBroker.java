package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBroker extends Remote{

	
	public void register(String ip, int port, String service) throws IllegalArgumentException, RemoteException;

	public String[] getTiffProcessors(int quantity) throws IllegalArgumentException, RemoteException;

	public int getTotalProcessors() throws RemoteException;

	public String[] getImageSerializers() throws IllegalArgumentException, RemoteException;
}

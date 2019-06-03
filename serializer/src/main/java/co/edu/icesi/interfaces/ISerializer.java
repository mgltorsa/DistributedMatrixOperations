package co.edu.icesi.interfaces;

/**
 * ISerializer
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.osoa.sca.annotations.Service;

@Service(interfaces = Runnable.class)
public interface ISerializer extends Remote {

    public void setWorkers(int workers) throws RemoteException;
	
    public void setDestPath(String path) throws RemoteException;
    
    public void setSourcePath(String path) throws RemoteException;
	
	public boolean isLocked() throws RemoteException;
	
    public void drawImage(int x, int y, int width, int height, int[][] image) throws RemoteException;
}
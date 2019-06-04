package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ISerializer
 */
public interface ISerializer extends Remote{

    public void setWorkers(int workers) throws RemoteException;
	
    public void setDestPath(String path) throws RemoteException;
    
    public void setSourcePath(String path) throws RemoteException;
	
	public boolean isLocked() throws RemoteException;
	
    public void drawImage(int x, int y, int width, int height, int[][] rotatedPoints) throws RemoteException;

    public boolean isPathLocked() throws RemoteException;

}
package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ITiffProcessor
 */
public interface ITiffProcessor extends Remote {

    public void processSource(int x, int y, int width, int height, double phi, String callbackserializer) throws RemoteException;
    public boolean isLocked();

}
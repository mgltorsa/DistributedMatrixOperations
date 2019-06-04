package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.osoa.sca.annotations.Service;

@Service
public interface IServer extends Remote{
	
	public void recieve(String sourcePath, String destPath, double phi) throws RemoteException;
	
}

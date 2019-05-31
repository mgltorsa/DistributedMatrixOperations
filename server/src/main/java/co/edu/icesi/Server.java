package co.edu.icesi;

import java.awt.Point;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.IMatrixOperations;
import co.edu.icesi.interfaces.IServer;

/**
 * Server
 */
public class Server extends UnicastRemoteObject implements IServer {

	
	private IBroker broker;
    
    @Property
	private String service;
	
	private List<IMatrixOperations> operations = new ArrayList<IMatrixOperations>();

    private static final long serialVersionUID = 1L;

    public Server() throws RemoteException {
        super();
    }

    @Reference(name="broker")
    public void setBroker(IBroker broker) {
    	this.broker=broker;
    }
    
    @Override
    public int[][] rotate(Point init, Point last){

		return new int[2][2];
	}

}
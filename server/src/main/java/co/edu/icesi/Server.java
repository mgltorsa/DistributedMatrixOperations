package co.edu.icesi;

import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import co.edu.icesi.interfaces.IServer;

/**
 * Server
 */
public class Server extends UnicastRemoteObject implements IServer {


    private static final long serialVersionUID = 1L;

    public Server() throws RemoteException {
        super();
    }

    @Override
    public int[][] rotate(Point init, Point last){

		return new int[2][2];
	}

}
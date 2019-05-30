package co.edu.icesi.interfaces;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote{

    public int[][] rotate(Point init, Point last) throws RemoteException;
}

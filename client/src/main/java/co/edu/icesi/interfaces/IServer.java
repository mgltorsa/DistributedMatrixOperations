package co.edu.icesi.interfaces;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer{

    public int[][] rotate(int[] initPoint, int[] lastPoint);

    public int[][] rotate(int[] initPoint, int[] lastPoint, int[] middlePoint);
    
}

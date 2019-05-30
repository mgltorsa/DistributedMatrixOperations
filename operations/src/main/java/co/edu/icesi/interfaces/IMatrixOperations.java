package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.osoa.sca.annotations.Service;


/**
 * IMatrixOperations
 */
@Service
public interface IMatrixOperations extends Remote{

    public int[][] rotateImage(int x, int y, int width, int height, double phi) throws RemoteException;

    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2) throws RemoteException;
}
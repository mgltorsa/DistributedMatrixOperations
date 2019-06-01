package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.osoa.sca.annotations.Service;

import co.edu.icesi.impl.ImageChunk;


/**
 * IMatrixOperations
 */
@Service
public interface IMatrixOperations extends Remote{

    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, int[] middlepoint, double phi) throws RemoteException;

    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, double phi) throws RemoteException;

    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2) throws RemoteException;

    public void writeImageChunk(String sourcePath, String destPath, ImageChunk chunk, int[][] points)  throws RemoteException;;
}
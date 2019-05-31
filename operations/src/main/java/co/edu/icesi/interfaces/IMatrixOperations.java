package co.edu.icesi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.osoa.sca.annotations.Service;


/**
 * IMatrixOperations
 */
@Service
public interface IMatrixOperations{

    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, int[] middlepoint, double phi);

    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, double phi);

    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2);

    public double[][] matrixMultiplication(double[] vector, double[][] matrix);
}
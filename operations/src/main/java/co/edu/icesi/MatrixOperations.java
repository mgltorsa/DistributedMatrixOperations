package co.edu.icesi;

import java.rmi.RemoteException;

import co.edu.icesi.interfaces.IMatrixOperations;

/**
 * MatrixOperations
 */
public class MatrixOperations implements IMatrixOperations {

    @Override
    public int[][] rotateImage(int x, int y, int width, int height, double phi) throws RemoteException {
        System.out.println("me rotaron raza");
        return null;
    }

    @Override
    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2) throws RemoteException {
        return null;
    }

    
}
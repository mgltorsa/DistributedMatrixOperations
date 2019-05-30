package co.edu.icesi.interfaces;

import java.rmi.RemoteException;

public interface IMatrixOperations {

	public int[][] rotateImage(int x, int y, int width, int height, double phi) throws RemoteException;

    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2) throws RemoteException;
}

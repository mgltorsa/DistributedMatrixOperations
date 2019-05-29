package co.edu.icesi.interfaces;

import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import org.osoa.sca.annotations.Service;


/**
 * IMatrixOperations
 */
@Service
public interface IMatrixOperations{

    public BufferedImage rotateImage(int x, int y, int width, int height, double phi) throws RemoteException;

    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2) throws RemoteException;
}
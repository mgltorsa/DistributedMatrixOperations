<<<<<<< HEAD
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
=======
package co.edu.icesi.interfaces;


import org.osoa.sca.annotations.Service;



/**
 * IMatrixOperations
 */
@Service
public interface IMatrixOperations{

    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, int[] middlepoint, double phi) ;

    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, double phi);

    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2);

>>>>>>> d4755263fc315e725c44e61eecea6eef0e37914e
}
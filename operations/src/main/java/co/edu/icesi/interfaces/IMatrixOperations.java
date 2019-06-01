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

}
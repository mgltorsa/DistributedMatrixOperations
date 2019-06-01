package co.edu.icesi.interfaces;


import org.osoa.sca.annotations.Service;


@Service
public interface IMatrixOperations{

	public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, int[] middlepoint, double phi);

    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, double phi);

    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2);

    public double[][] matrixMultiplication(double[] vector, double[][] matrix);
}

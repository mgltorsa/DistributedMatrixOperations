package co.edu.icesi;


import org.osoa.sca.annotations.Reference;

import co.edu.icesi.interfaces.IMatrixOperations;
import co.edu.icesi.vectors.IVectorOperations;

/**
 * MatrixOperations
 */
public class MatrixOperations implements IMatrixOperations {
    
    @Reference
    private IVectorOperations vectorOperations;

    @Override
    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2) {
        return null;
    }

    @Override
    public double[] matrixMultiplication(double[] vector, double[][] matrix) {
        return null;
    }

    @Override
    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, int[] middlepoint, double phi) {

        double[][] rotationMatrix= getRotationMatrix(phi);
        
        int dimension = initPoint.length;
        
        int n = getPointsInRegion(initPoint, lastPoint);
        int[][] listOfRotatedPoints = new int[n][dimension];

        for(int i=initPoint[0];i<lastPoint[0];i++){
            for (int j = initPoint[1]; j < lastPoint[1]; j++) {
                double[] vector = new double[]{i,j};
                double[][] tempRotatedPoints = matrixMultiplication(vector, rotationMatrix);


            }
        }

        return listOfRotatedPoints;
    }

    @Override
    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, double phi) {
       return null;
    }

    private int getPointsInRegion(int[] initPoint, int[] lastPoint) {
        
        int dx = lastPoint[0]-initPoint[0];
        int dy = lastPoint[1]-lastPoint[1];
        return dx*dy;
    }

    private double[][] getRotationMatrix(double phi) {
        
        double cosPhi = Math.cos(Math.toRadians(phi));
		double sinPhi = Math.sin(Math.toRadians(phi));
		return new double[][] {{cosPhi, -sinPhi},
            {sinPhi, cosPhi}};
       
    }

    

    
    
}
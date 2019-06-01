package co.edu.icesi;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.osoa.sca.annotations.Reference;

import co.edu.icesi.interfaces.IMatrixOperations;
import co.edu.icesi.vectors.IVectorOperations;

/**
 * MatrixOperations
 */
public class MatrixOperations extends UnicastRemoteObject implements IMatrixOperations {
    
    @Reference
    private IVectorOperations vectorOperations;

    public MatrixOperations() throws RemoteException{
        super();
    }

    @Override
    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2) {
        
        double[][] transposedMatrix = transponse(matrix2);
        double[][] result = new double[matrix.length][matrix2[0].length];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = vectorOperations.dotProduct(matrix[i],transposedMatrix[j]);
            }
        }
        return result;
        
    }
   

    @Override
    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, int[] middlepoint, double phi) {

        double[][] rotationMatrix= getRotationMatrix(phi);
        
        int dimension = initPoint.length;
        
        int n = getPointsInRegion(initPoint, lastPoint);
        int[][] listOfRotatedPoints = new int[n][dimension];

        int[] dMiddlePoint = getDMiddlePoint(middlepoint);

        int currentPoint=0;


        for(int i=initPoint[0];i<lastPoint[0];i++){
            for (int j = initPoint[1]; j < lastPoint[1]; j++) {
                double[][] vector = new double[][]{{i},{j}};
                double[][] tempRotatedPoint = matrixMultiplication(rotationMatrix, vector);
                int[] rotatedPoint = new int[tempRotatedPoint.length];
                for (int k  = 0; k < tempRotatedPoint.length; k++) {
                    rotatedPoint[k] = (int) tempRotatedPoint[k][0]+dMiddlePoint[k];
                }
                
                listOfRotatedPoints[currentPoint] = rotatedPoint;
                currentPoint++;         

            }
        }

        return listOfRotatedPoints;
    }

    @Override
    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, double phi) {

       return rotatePointsInRegion(initPoint, lastPoint, new int[]{0,0}, phi);
    }

    public double[][] transponse(double[][] matrix){
        double[][] transposed = new double[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix[0].length; i++) {

            for (int j = 0; j < matrix.length; j++) {
                transposed[i][j] = matrix[j][i];
            }
                        
        }

        return transposed;
    }


    private int getPointsInRegion(int[] initPoint, int[] lastPoint) {
        
        int dx = lastPoint[0]-initPoint[0];
        int dy = lastPoint[1]-lastPoint[1];
        return dx*dy;
    }

    private int[] getDMiddlePoint(int[] middlepoint){
        int dx = middlepoint[0];
        int dy= middlepoint[1];
        return null; 
    }

    private double[][] getRotationMatrix(double phi) {
        
        double cosPhi = Math.cos(Math.toRadians(phi));
		double sinPhi = Math.sin(Math.toRadians(phi));
		return new double[][] {{cosPhi, -sinPhi},
            {sinPhi, cosPhi}};       
    }

    

    
    
}
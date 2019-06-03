<<<<<<< HEAD
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

    
=======
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
        
        double[][] transposedMatrix = transponse(matrix2);

        // printMatrix("matrix1", matrix);
        double[][] result = new double[matrix.length][matrix2[0].length];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {

                result[i][j] = vectorOperations.dotProduct(matrix[i],transposedMatrix[j]);
            }
        }

        // printMatrix("result", result);
        return result;
        
    }

    private void printMatrix(String r, double[][] res){
        System.out.println(r+": Matrix: {");
			for (int i = 0; i < res.length; i++) {
				for (int j = 0; j < res[0].length; j++) {
					System.out.print(res[i][j]+",");
				}
				System.out.println();
			}
			System.out.println("}");
    }
   

    @Override
    public int[][] rotatePointsInRegion(int[] initPoint, int[] lastPoint, int[] middlepoint, double phi) {

        double[][] rotationMatrix= getRotationMatrix(phi);
        
        int dimension = initPoint.length;
        
        int n = getPointsInRegion(initPoint, lastPoint);

        //n+2 porque se necesitan los puntos extremos de la imagen 
        //x,y = esquinas del rectangulo
        int[][] listOfRotatedPoints = new int[dimension][n+2];


        //n = {{x,x1,x2,x3,x4...xn},{y,y1,y2,y3,...yn,yn}}
        System.out.println(n);
        int[] dMiddlePoint = getDMiddlePoint(middlepoint);

        int xIndex = 0;
        int yIndex = 1;
        int currentIndex = 0;
        int xleft = 0;
        int xright = 0;
        int ytop = 0;
        int ybottom = 0;
        for(int i=initPoint[0];i<lastPoint[0];i++){
            for (int j = initPoint[1]; j < lastPoint[1]; j++) {
                double[][] vector = new double[][]{{i},{j}};
                double[][] tempRotatedPoint = matrixMultiplication(rotationMatrix, vector);
            
                int[] rotatedPoint = new int[tempRotatedPoint.length];
                for (int k  = 0; k < tempRotatedPoint.length; k++) {
                    rotatedPoint[k] =(int)tempRotatedPoint[k][0];
                }

                int x = rotatedPoint[0];
                int y = rotatedPoint[1];

                System.out.println("x:->> " + x + "y:->> "+y);
                if(xleft > x)
                    xleft= x;
                if(xright < x)
                    xright = x;
                if(ytop < y)
                    ytop= y;
                if(ybottom > y)
                    ybottom= y;
                listOfRotatedPoints[xIndex][currentIndex] = rotatedPoint[0];
                listOfRotatedPoints[yIndex][currentIndex] = rotatedPoint[1];

                currentIndex++;         
            }
        }

        listOfRotatedPoints[xIndex][n] = xleft;
        listOfRotatedPoints[xIndex][n+1] = xright;
        listOfRotatedPoints[yIndex][n] = ybottom;
        listOfRotatedPoints[yIndex][n+1] = ytop;

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
        // printMatrix("trans",transposed);

        return transposed;
    }


    private int getPointsInRegion(int[] initPoint, int[] lastPoint) {
        
        int dx = lastPoint[0]-initPoint[0];
        int dy = lastPoint[1]-initPoint[1];
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
    
>>>>>>> d4755263fc315e725c44e61eecea6eef0e37914e
}
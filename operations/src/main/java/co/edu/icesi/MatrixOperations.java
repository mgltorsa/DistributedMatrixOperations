package co.edu.icesi;

import java.util.Arrays;

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

        //Corners of the image rotated
        int[][] corners = imageCornersRotated(lastPoint[0]-initPoint[0], lastPoint[1]-initPoint[1], phi);

        //n+2 porque se necesitan los puntos extremos de la imagen 
        //x,y = esquinas del rectangulo
        int[][] listOfRotatedPoints = new int[dimension][n+2];


        //n = {{x,x1,x2,x3,x4...xn},{y,y1,y2,y3,...yn,yn}}
        System.out.println(n);
        int[] dMiddlePoint = getDMiddlePoint(middlepoint);

        int xIndex = 0;
        int yIndex = 1;
        int currentIndex = 0;
        for(int i=initPoint[0];i<lastPoint[0];i++){
            for (int j = initPoint[1]; j < lastPoint[1]; j++) {
                double[][] vector = new double[][]{{i},{j}};
                double[][] tempRotatedPoint = matrixMultiplication(rotationMatrix, vector);
            
                int[] rotatedPoint = new int[tempRotatedPoint.length];
                for (int k  = 0; k < tempRotatedPoint.length; k++) {
                    rotatedPoint[k] =(int)tempRotatedPoint[k][0];
                }

                listOfRotatedPoints[0][currentIndex]=rotatedPoint[0];
				listOfRotatedPoints[1][currentIndex]=rotatedPoint[1];

                System.out.println("x:->> " + x + "y:->> "+y);
                currentIndex++;         
            }
        }

        listOfRotatedPoints[xIndex][n] = corners[0][0];
        listOfRotatedPoints[xIndex][n+1] = corners[0][1];
        listOfRotatedPoints[yIndex][n] = corners[1][0];
        listOfRotatedPoints[yIndex][n+1] = corners[1][1];

        return listOfRotatedPoints;
    }

    private int[][] imageCornersRotated(int width, int height, double phi) {
		int[][] points = new int[2][2];

		double phiRadians = Math.toRadians(phi);

		double cos = Math.cos(phiRadians);
		double sin = Math.sin(phiRadians);
        
    
        
		double[] xs = new double[] { 0, cos * width, -sin * height, cos * width - sin * height };
		double[] ys = new double[] { 0, sin * width, cos * height, sin * width + cos * width };

        
        
		Arrays.sort(xs);
		Arrays.sort(ys);

		points[0][0] = (int) xs[0];
		
		points[0][1] = (int) xs[3];
		
		points[1][0] = (int) ys[0];
		
		points[1][1] = (int) ys[3];

		return points;
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
    
}
package com.archisoft;

import com.archisoft.interfaces.*;
import com.archisoft.multipliers.VectorMultiplier;


/**
 * Hello world!
 *
 */
public class MatrixMultiplier implements IMatrixOperations {


    private IVectorMultiplier vectorMultiplier = new VectorMultiplier();

    
    @Override
    public double[][] matrixMultiplication(double[][] matrix1, double[][] matrix2)  {
        double[][] result = new double[matrix1.length][matrix2[0].length];
        trasponse(matrix2);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = vectorMultiplier.multiplyVector(matrix1[i], matrix2[i]);
            }
        }
        return result;
    }

    private double[][] trasponse(double[][] matrix) {

        double[][] cloned = new double[matrix[0].length][matrix.length];
        int n = matrix[0].length;
        int m = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cloned[i][j] = matrix[j][i];
            }
        }

        return cloned;
    }


}

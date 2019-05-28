package com.archisoft.interfaces;



import org.osoa.sca.annotations.Service;

/**
 * IMatrixMultiplier
 */

@Service
public interface IMatrixOperations {

    double[][] matrixMultiplication(double[][] matrix1, double[][] matrix2) ;

    double[][] rotate(double[][] matrix, double phi);
}
package co.edu.icesi.interfaces;

import java.awt.image.BufferedImage;

/**
 * IMatrixOperations
 */
public interface IMatrixOperations {

    public BufferedImage rotateImage(int x, int y, int width, int height, double phi);

    public double[][] matrixMultiplication(double[][] matrix, double[][] matrix2);
}
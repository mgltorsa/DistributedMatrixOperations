package com.archisoft.implementation;

import java.awt.image.AreaAveragingScaleFilter;
import java.text.DecimalFormat;
import java.time.format.DecimalStyle;
import java.util.*;

import com.archisoft.interfaces.*;

/**
 * Hello world!
 *
 */
public class MatrixOperations implements IMatrixOperations {

	private IVectorMultiplier vectorMultiplier = new VectorMultiplier();

	@Override
	public double[][] matrixMultiplication(double[][] matrix1, double[][] matrix2) {
		double[][] result = new double[matrix1.length][matrix2[0].length];
		trasponse(matrix2);

		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = vectorMultiplier.multiplyVector(matrix1[i], matrix2[i]);
			}
		}
		return result;
	}

	@Override
	public double[][] rotate(double[][] matrix, double phi) {

		// Transform input degree into radiant
		double radiant = (phi / 180) * Math.PI;

		ArrayList<Pair> newPairs = new ArrayList<>();
		double[][] mr = new double[2][2];

		// Generates matrix rotation
		mr[0][0] = Math.cos(radiant);
		mr[0][1] = -Math.sin(radiant);
		mr[1][0] = Math.sin(radiant);
		mr[1][1] = Math.cos(radiant);

		double min = 0.00000000001;

		// Fix matrix rotation for having double numbers with 6 significant decimals
		for (int m = 0; m < 2; m++) {
			for (int k = 0; k < 2; k++) {
				double abs = Math.abs(mr[m][k]);
				if (abs < min && 0.0 < abs)
					mr[m][k] = 0;
			}
		}

		System.out.printf("Cos(phi) %f Sin(phi) %f \n", Math.cos(phi), Math.sin(phi));

		int left = 0, right = 0, bottom = 0, top = 0;

		for (int i = 0; i < mr.length; i++) {
			for (int j = 0; j < mr[0].length; j++) {

				// Get the rotation of a point (x,y)
				int ni = (int) ((i * mr[0][0] + mr[0][1] * j));
				int nj = (int) ((i * mr[1][0] + mr[1][1] * j));

				// Save the point (x,y) rotated
				Pair p = new Pair(ni, nj, i, j);
				newPairs.add(p);

				System.out.printf("i %d j %d ni %d nj %d \n", i, j, ni, nj);

				// Change variables for fitting
				if (nj < bottom)
					bottom = nj;
				else if (nj > top)
					top = nj;

				if (ni < left)
					left = ni;
				else if (ni > right)
					right = ni;

			}
		}

		System.out.printf("Left %d Right %d Bottom %d Top %d \n", left, right, bottom, top);

		int width = Math.abs(left - right) + 1;
		int height = Math.abs(top - bottom) + 1;

		double[][] matrixRotated = new double[height][width];

		return matrix;
	}

	private int[] matrixSize(double phi, int width, int height) {

		double sin = Math.sin(phi);
		double cos = Math.cos(phi);

		// Bottom left corner
		int bli = (int) (0 * cos + 0 * sin);
		int blj = (int) (0 * -sin + 0 * cos);

		// Bottom right corner
		int bri = (int) (0 * cos + width * sin);
		int brj = (int) (0 * -sin + width * cos);

		// Top left corner
		int tli = (int) (height * cos + 0 * sin);
		int tlj = (int) (height * -sin + 0 * cos);

		// Top right corner
		int tri = (int) (height * cos + width * sin);
		int trj = (int) (height * -sin + width * cos);

		int[] size = new int[2];

		if ((0 < phi && phi <= 90) || (180 < phi && 370 <= phi)) {
			size[0] = Math.abs(tlj - brj) <= width ? width : Math.abs(tlj - brj);
			size[1] = Math.abs(tri - bli) < height ? height : Math.abs(tri - bli);
		}

		else if ((90 < phi && phi <= 180) || (270 < phi && phi <= 360)) {
			size[0] = Math.abs(trj - blj) <= height ? width : Math.abs(trj - blj);
			size[1] = Math.abs(bri - tli) <= height ? height : Math.abs(trj - blj);
		}

		return size;
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

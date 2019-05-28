package com.archisoft;

import java.util.ArrayList;

import com.archisoft.implementation.Pair;

public class Test {

	private static int[] size(double phi, int width, int height) {

		return null;
	}

	public static void main(String[] args) {

		// Transform input degree into radiant
		double radiant = (90.0 / 180.0) * Math.PI;

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

		System.out.printf("Cos(phi) %f Sin(phi) %f \n", Math.cos(radiant), Math.sin(radiant));

		int[][] matrix1 = new int[2][2];

		matrix1[0][0] = 1;
		matrix1[0][1] = 2;
		matrix1[1][0] = 3;
		matrix1[1][1] = 4;

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

		int deltaWidth = left * -1;
		int deltaHeight = bottom * -1;

		int width = Math.abs(left - right) + 1;
		int height = Math.abs(top - bottom) + 1;

		double[][] matrixRotated = new double[height][width];

		int[][] matrix2 = new int[2][2];

		for (Pair pair : newPairs) {
			int[] vector = pair.getVector();
			vector[0] += deltaWidth;
			vector[1] += deltaHeight;
			
			matrix2[vector[0]][vector[1]] = matrix1[pair.getXo()][pair.getYo()];

		}

		for (int m = 0; m < 2; m++) {
			String str = "";
			for (int k = 0; k < 2; k++) {
				str += " " + matrix2[m][k];
			}
			
			System.out.println(str);
		}
	}

}

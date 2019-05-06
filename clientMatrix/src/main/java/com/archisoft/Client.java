package com.archisoft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

import com.archisoft.interfaces.IMatrixOperations;

import org.osoa.sca.annotations.Reference;

/**
 * Hello world!
 *
 */
public class Client implements Runnable {

    @Reference(required = true)
    private IMatrixOperations matrixOperations;

    @Override
    public void run() {

        System.out.println("poeroxo");
        System.out.println("running a test");
        double[][] matrix1 = new double[2][2];
        double[][] matrix2 = new double[2][2];

        matrix1[0][0] = 2;
        matrix1[0][1] = 1;
        matrix1[1][0] = 0;
        matrix1[1][1] = 2;

        matrix2[0][0] = 5;
        matrix2[0][1] = 4;
        matrix2[1][0] = 3;
        matrix2[1][1] = 2;

        double[][] result = matrixOperations.matrixMultiplication(matrix1, matrix2);

        printMatrix(result);

        System.out.println("running with file in properties");

        ArrayList<double[][]> matrixes = loadMatrixes();
        matrix1 = matrixes.get(0);
        matrix2 = matrixes.get(1);

        result = matrixOperations.matrixMultiplication(matrix1, matrix2);

        printMatrix(result);

    }

    private ArrayList<double[][]> loadMatrixes() {

        Properties properties = new Properties();
        ArrayList<double[][]> matrixes = new ArrayList<double[][]>();
        try {
            properties.load(getClass().getResourceAsStream("properties"));
            String matrix1Path = properties.getProperty("matrix1");
            String matrix2Path = properties.getProperty("matrix2");

            matrixes.add(loadMatrix(matrix1Path));
            matrixes.add(loadMatrix(matrix2Path));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return matrixes;
    }

    private double[][] loadMatrix(String matrixPath) {

        double[][] matrix = null;
        try {
            File file = new File(matrixPath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String info[] = br.readLine().split(" ");
            int n = Integer.parseInt(info[0]);
            int m = Integer.parseInt(info[1]);

            matrix = new double[n][m];

            for (int i = 0; i < n; i++) {
                String[] strNumbers = br.readLine().split(" ");

                for (int j = 0; j < m; j++) {
                    matrix[i][j] = Double.parseDouble(strNumbers[j]);
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return matrix;
    }

    private void printMatrix(double[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        System.out.println("{");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j < m - 1) {
                    System.out.print(matrix[i][j] + ", ");
                } else {
                    System.out.println(matrix[i][j]);
                }

            }
        }

        System.out.println("}");
    }

}

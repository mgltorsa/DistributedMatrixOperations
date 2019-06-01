package co.edu.icesi.vectors;

/**
 * VectorOperations
 */
public class VectorOperations implements IVectorOperations{

    @Override
    public double dotProduct(double[] vector1, double[] vector2) {
        double dotProduct=0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i]*vector2[i];
        }
        return dotProduct;
    }

    
}
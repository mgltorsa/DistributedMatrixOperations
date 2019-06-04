package co.edu.icesi.vectors;

import org.osoa.sca.annotations.Service;

/**
 * IVectorMultiplier
 */
@Service
public interface IVectorOperations {

    public double dotProduct(double[] vector1, double[] vector2);


}
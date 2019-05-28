package com.archisoft.implementation;



import com.archisoft.interfaces.IVectorMultiplier;

/**
 * VectorMultiplier
 */
public class VectorMultiplier implements IVectorMultiplier{

    @Override
    public double multiplyVector(double[] vector1, double[] vector2) {
        
        double result=0.0;

        for(int i=0;i<vector1.length;i++){
            result+=vector1[i]*vector2[i];
        }

        return result;
    }
    
}
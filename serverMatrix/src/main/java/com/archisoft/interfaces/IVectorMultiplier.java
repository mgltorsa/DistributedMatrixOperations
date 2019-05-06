package com.archisoft.interfaces;

import org.osoa.sca.annotations.Service;

/**
 * IVectorMultiplier
 */
@Service
public interface IVectorMultiplier {

    public double multiplyVector(double[] vector1, double[] vector2);
}
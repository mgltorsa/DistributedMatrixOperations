package com.archisoft.interfaces;

import org.osoa.sca.annotations.Service;

@Service
public interface IMultiplicationVectors {
	
	public double multipication(double[] v1,double[] v2);//throws RemoteException;

}

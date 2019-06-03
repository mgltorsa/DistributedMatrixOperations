package co.edu.icesi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.osoa.sca.annotations.Reference;

import co.edu.icesi.interfaces.IMatrixOperations;
import co.edu.icesi.interfaces.ITiffProcessor;

/**
 * TiffProcessor
 */
public class TiffProcessor extends UnicastRemoteObject implements ITiffProcessor {
	
	private static final long serialVersionUID = 1L;

	public TiffProcessor() throws RemoteException {
		super();
	}

	@Reference
	private IMatrixOperations matrixOperations;

	@Override
	public void processSource(String source, int x, int y, int width, int height,double phi) {
		int[] initPoint = {x,y};
		int[] lastPoint = {x+width,y+height};
		int[][] rotatedPoints = matrixOperations.rotatePointsInRegion(initPoint, lastPoint, phi);
		

	}

	@Reference(name="operations")
	public void setMatrixOperations(IMatrixOperations matrixOperations){
		this.matrixOperations = matrixOperations;
	}

	
}
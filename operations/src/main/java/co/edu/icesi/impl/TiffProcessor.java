package co.edu.icesi.impl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.osoa.sca.annotations.Reference;

import co.edu.icesi.interfaces.IMatrixOperations;
import co.edu.icesi.interfaces.ISerializer;
import co.edu.icesi.interfaces.ITiffProcessor;

/**
 * TiffProcessor
 */
public class TiffProcessor extends UnicastRemoteObject implements ITiffProcessor {
	
	private static final long serialVersionUID = 1L;

	private static boolean lock = false;

	public TiffProcessor() throws RemoteException {
		super();
	}

	@Reference
	private IMatrixOperations matrixOperations;

	@Override
	public void processSource(int x, int y, int width, int height,double phi, String callbackserializer) {
		lock=true;
		int[] initPoint = {x,y};
		int[] lastPoint = {x+width,y+height};
		int[][] rotatedPoints = matrixOperations.rotatePointsInRegion(initPoint, lastPoint, phi);

		ISerializer serializer = getImageSerializer(callbackserializer);
		serializer.drawImage(x, y, width, height, rotatedPoints);
		lock=false;
		

	}

	public ISerializer getImageSerialize(String rmiEncodedUrl){
		String[] info = rmiEncodedUrl.split(":");
		String ip = info[0];
		String port = info[1];
		String service = info[2];
		return (ISerializer) Naming.lookup("rmi://"+ip+":"+port+"/"+service);
	}

	@Reference(name="operations")
	public void setMatrixOperations(IMatrixOperations matrixOperations){
		this.matrixOperations = matrixOperations;
	}

	public boolean isLocked(){
		return lock;
	}


	
}
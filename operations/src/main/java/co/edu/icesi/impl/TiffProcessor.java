package co.edu.icesi.impl;

import java.io.Serializable;
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
public class TiffProcessor extends UnicastRemoteObject implements ITiffProcessor, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static boolean lock = false;
	
	private IMatrixOperations matrixOperations;

	public TiffProcessor() throws RemoteException {
		super();
	}


	@Override
	public void processSource(int x, int y, int width, int height,double phi, String callbackserializer) {
		lock=true;
		System.out.println("locked");
		int[] initPoint = {x,y};
		int[] lastPoint = {x+width,y+height};
		int[][] rotatedPoints = matrixOperations.rotatePointsInRegion(initPoint, lastPoint, phi);
		System.out.println("rotated with length -> "+rotatedPoints.length);
		
		try {
			System.out.println("to rotate to-> "+callbackserializer );
			ISerializer serializer = getImageSerializer(callbackserializer);
			while(serializer.isLocked()){
					System.out.println("serializer is locked");
					Thread.sleep(1000);
				}		
			System.out.println("to draw image");
			serializer.drawImage(x, y, width, height, rotatedPoints);
			Thread.sleep(5000);
			System.out.println("drawed image");
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		
		lock=false;
		System.out.println("unlocked");
		

	}

	public ISerializer getImageSerializer(String rmiEncodedUrl){
		try {
			String[] info = rmiEncodedUrl.split(":");
			String ip = info[0];
			String port = info[1];
			String service = info[2];
			return (ISerializer) Naming.lookup("rmi://"+ip+":"+port+"/"+service);
			
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Reference(name="operations")
	public void setMatrixOperations(IMatrixOperations matrixOperations){
		this.matrixOperations = matrixOperations;
	}

	public boolean isLocked(){
		return lock;
	}


	
}
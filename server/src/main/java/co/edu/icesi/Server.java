
package co.edu.icesi;

import java.awt.Point;
import java.awt.Rectangle;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.IServer;
import co.edu.icesi.interfaces.ITiffManager;
import co.edu.icesi.interfaces.ITiffProcessor;

/**
 * Server
 */
public class Server implements IServer, Runnable {


	private IBroker broker;

	@Property
	private String service;

	@Reference
	private ITiffManager tiffManager;


	@Reference(name = "broker")
	public void setBalancer(IBroker broker) {
		this.broker = broker;
	}

	@Override
	public void run() {
		// runTest();
	}

	// private void runTest() {

	// 	String[] info = broker.getMultiplicationService().split(":");
	// 	String ip = info[0];
	// 	String port = info[1];

	// 	System.out.println(ip + ":" + port);
	// 	try {
	// 		operations = (IMatrixOperations) Naming.lookup("rmi://" + ip + ":" + port + "/" + service);
	// 		System.out.println(operations);

	// 		double cosPhi = Math.cos(Math.toRadians(45));
	// 		double sinPhi = Math.sin(Math.toRadians(45));
	// 		double[][] res = operations.matrixMultiplication(new double[][] {{cosPhi, -sinPhi},
    //         {sinPhi, cosPhi}},
	// 				new double[][] { { 0 }, { 499 } });
	// 		System.out.println("Matrix: {");
	// 		for (int i = 0; i < res.length; i++) {
	// 			for (int j = 0; j < res[0].length; j++) {
	// 				System.out.print(res[i][j]+",");
	// 			}
	// 			System.out.println();
	// 		}
	// 		System.out.println("}");

	// 	} catch (Exception e) {
	// 		// TODO: handle exception
	// 		e.printStackTrace();
	// 	}
	// }


	@Override
	public void recieve(final String sourcePath, String destPath, final double phi) {
		// TODO Auto-generated method stub
		int cores =  broker.getTotalProcessors();
		List<Rectangle> rectangles =  tiffManager.calculateRegions(sourcePath, cores);
		int rectanglesPerCore = rectangles.size()/cores;
		final String[] processors = broker.getTiffProcessors(cores);

		for (int i = 0; i < processors.length; i++) {
			final Rectangle[] threadRectangles = new Rectangle[rectanglesPerCore];
			//Repite regiones, arreglar TODO:
			rectangles.toArray(threadRectangles);

			try{
				final ITiffProcessor processor =  (ITiffProcessor) Naming.lookup("rmi://"+processors[i]+"/"+service);

				new Thread(new Runnable(){
			
					@Override
					public void run() {
						for (int j = 0; j < threadRectangles.length; j++) {
							try{
								Rectangle rectangle = threadRectangles[j];
								int x = (int) rectangle.getX();
								int y = (int) rectangle.getY();
								int width = (int) rectangle.getWidth();
								int height = (int) rectangle.getHeight();
								processor.processSource(sourcePath, x, y, width, height, phi);
							}catch(Exception e){
								e.printStackTrace();
							}
							
						}
	
						
					}
				}).start();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

		
		// Permitir a los procesadores iniciar trabajo
	}

}
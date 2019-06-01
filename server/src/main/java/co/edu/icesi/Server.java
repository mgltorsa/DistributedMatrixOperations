package co.edu.icesi;

import java.awt.Point;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

import co.edu.icesi.impl.ImageChunk;
import co.edu.icesi.impl.ImageScheduler;
import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.IMatrixOperations;
import co.edu.icesi.interfaces.IServer;

/**
 * Server
 */
public class Server implements IServer, Runnable {

	private ImageScheduler scheduler;

	private IBroker broker;

	@Property
	private String service;

	private static IMatrixOperations operations;

	@Reference(name = "broker")
	public void setBalancer(IBroker broker) {
		this.broker = broker;
		this.scheduler = new ImageScheduler();
	}

	@Override
	public void run() {
		runTest();
	}

	private void runTest() {

		String[] info = broker.getMultiplicationService().split(":");
		String ip = info[0];
		String port = info[1];

		System.out.println(ip + ":" + port);
		try {
			operations = (IMatrixOperations) Naming.lookup("rmi://" + ip + ":" + port + "/" + service);
			System.out.println(operations);

			double cosPhi = Math.cos(Math.toRadians(45));
			double sinPhi = Math.sin(Math.toRadians(45));
			double[][] res = operations.matrixMultiplication(new double[][] {{cosPhi, -sinPhi},
            {sinPhi, cosPhi}},
					new double[][] { { 0 }, { 499 } });
			System.out.println("Matrix: {");
			for (int i = 0; i < res.length; i++) {
				for (int j = 0; j < res[0].length; j++) {
					System.out.print(res[i][j]+",");
				}
				System.out.println();
			}
			System.out.println("}");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	@Override
	public void recieve(String sourcePath, String destPath, Double phi) {
		// TODO Auto-generated method stub
		scheduler.setImageSource(sourcePath);
		scheduler.setImageDestination(destPath);
		scheduler.getImageFileProcessor().setImageProperties();
		scheduler.getImageProcessor().splitImage();

		ImageChunk ic = scheduler.getImageProcessor().retriveImageChunk();

		String[] info = broker.getMultiplicationService().split(":");
		String ip = info[0];
		String port = info[1];

		System.out.println(ip + ":" + port);
		System.out.println(ic.getHeight() + " " +ic.getWidth());
		try {
			operations = (IMatrixOperations) Naming.lookup("rmi://" + ip + ":" + port + "/" + service);
			
			int[][] res = operations.rotatePointsInRegion(new int[] { 0, 0 },
					new int[] { ic.getWidth(), ic.getHeight() }, phi);

			scheduler.getImageFileProcessor().saveImageChunk(res, ic);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("Hola");

		// Permitir a los procesadores iniciar trabajo
	}

}
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

	private IMatrixOperations operations;

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

			double[][] res = operations.matrixMultiplication(new double[][] { { 1 }, { 2 } },
					new double[][] { { 1 }, { 2 } });
			System.out.println(res);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public int[][] rotate(int[] initPoint, int[] lastPoint) {

		return null;
	}

	@Override
	public int[][] rotate(int[] initPoint, int[] lastPoint, int[] middlePoint) {
		return null;
	}

	@Override
	public void recieve(String sourcePath, String destPath, Double phi) {
		// TODO Auto-generated method stub
		scheduler.setImageSource(sourcePath);
		scheduler.setImageDestination(destPath);
		scheduler.getImageProcessor().splitImage(500, 500);

		ImageChunk ic = scheduler.getImageProcessor().retriveImageChunk();

		String[] info = broker.getMultiplicationService().split(":");
		String ip = info[0];
		String port = info[1];

		System.out.println(ip + ":" + port);
		try {
			operations = (IMatrixOperations) Naming.lookup("rmi://" + ip + ":" + port + "/" + service);
			
			int[][] res = operations.rotatePointsInRegion(new int[] { 0, 0 },
					new int[] { ic.getWidth(), ic.getHeight() }, phi);
			System.out.println(res);

			for (int i = 0; i < res.length; i++) {
				String line = "";
				for (int j = 0; j < res[0].length; j++) {
					line += res[i][j];
				}
				System.out.println(line);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("Hola");

		// Permitir a los procesadores iniciar trabajo
	}

}
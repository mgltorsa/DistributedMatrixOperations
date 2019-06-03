
package co.edu.icesi;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

import co.edu.icesi.interfaces.IBroker;
import co.edu.icesi.interfaces.ISerializer;
import co.edu.icesi.interfaces.IServer;
import co.edu.icesi.interfaces.ITiffManager;
import co.edu.icesi.interfaces.ITiffProcessor;

/**
 * Server
 */
public class Server implements IServer, Runnable {


	private IBroker broker;

	private static int currentSerializer = 0;

	private static String[] serializers;

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
	public void recieve(String sourcePath, String destPath, double phi) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		
			
		File directory = getDirectory(sourcePath);
		File[] files = getImagesInDirectory(directory);
		serializers= broker.getImageSerializers();
		
		for (File file : files) {

			startFileProcessThread(file.getAbsolutePath(), destPath, phi, serializers[currentSerializer++]);
		}	

		

		
		// Permitir a los procesadores iniciar trabajo
	}

	private void startFileProcessThread(final String absolutePath, final String destPath,final double phi,final String callbackserializer) {
		new Thread(new Runnable(){
		
			@Override
			public void run() {
				ISerializer serializer = getImageSerializer(callbackserializer);
				while(serializer.isLocked()){
					Thread.sleep(1000);
				}
				serializer.setDestPath(destPath);
				processImage(absolutePath, destPath, phi, callbackserializer);
			}

			
		}).start();
	}

	private ISerializer getImageSerializer(String rmiEncodedUrl) {
		String[] info = rmiEncodedUrl.split(":");
		String ip = info[0];
		String port = info[1];
		String service = info[2];
		return (ISerializer) Naming.lookup("rmi://"+ip+":"+port+"/"+service);
	}

	private void processImage(String imagePath, String destPath, double phi, String callbackserializer) {
		
		int cores =  broker.getTotalProcessors();
		List<Rectangle> rectangles =  tiffManager.calculateRegions(imagePath);
		int rectanglesPerCore = rectangles.size()==1 ? 1 : rectangles.size()/cores;
		final String[] processors = broker.getTiffProcessors(Math.min(rectangles.size(), cores));	
		
		int currentRectangleOffset= 0;


		
		//For each processor we will process an image region.
		for (int i = 0; i < processors.length; i++) {

			Rectangle[] threadRectangles = new Rectangle[rectanglesPerCore];
			rectangles.subList(currentRectangleOffset, rectanglesPerCore);

			currentRectangleOffset+=rectanglesPerCore;
			
			runThreadProcessor(processors[i], threadRectangles, phi, callbackserializer);

		}
			
	}

	private void runThreadProcessor(final String processorStr, final Rectangle[] threadRectangles, final double phi, final String callbackserializer) {
		new Thread(new Runnable(){
		
			@Override
			public void run() {
				for (int i = 0; i < threadRectangles.length; i++) {
					ITiffProcessor processor = getProcessor(processorStr);
					Rectangle rectangle = threadRectangles[i];
					int x = (int) rectangle.getX();
					int y = (int) rectangle.getY();
					int width = (int) rectangle.getWidth();
					int height = (int) rectangle.getHeight();
					while(processor.isLocked()){
						Thread.sleep(1000);
					}
					processor.processSource(x, y, width, height, phi, callbackserializer);
				}
			}

			
		}).start();

	}

	private ITiffProcessor getProcessor(String rmiEncodedUrl) {

		String[] info = rmiEncodedUrl.split(":");
		String ip = info[0];
		String port = info[1];
		String service = info[2];
		return (ITiffProcessor) Naming.lookup("rmi://"+ip+":"+port+"/"+service);
	}

	private File[] getImagesInDirectory(File directory) {
		File[] files = directory.listFiles();
		List<File> imagesFilesList = new ArrayList<File>();
		for (File file : files) {
			if(tiffManager.isImage(file)){
				imagesFilesList.add(file);
			}
		}
		File[] imageFiles = new File[imagesFilesList.size()];
		return imagesFilesList.toArray(imageFiles);
	}

	

	private File getDirectory(String sourcePath) {
		boolean isDirectory = Files.isDirectory(Paths.get(sourcePath), LinkOption.NOFOLLOW_LINKS);
		if(!isDirectory){
			throw new IllegalArgumentException("path is not a directory");
		}
		File directory = new File(sourcePath);
		return directory;
	}

}
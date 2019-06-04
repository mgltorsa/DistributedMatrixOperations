
package co.edu.icesi;

import java.awt.Rectangle;
import java.io.File;

import java.rmi.Naming;
import java.rmi.RemoteException;
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
		

		int i = 0;
		for (File file : files) {
			
			String callbackserializer = getNextSerializer();
			ISerializer serializer = getImageSerializer(callbackserializer);
			System.out.println("wait if serialier is pathlocked");
			waitWhileSerializerIsPathLocked(serializer);
			String realDestPath = destPath+"/"+ i + file.getName();
			System.out.println("processing-> "+file.getAbsolutePath());
			
			try {
				serializer.setDestPath(realDestPath);
				serializer.setSourcePath(file.getAbsolutePath());
				System.out.println("setted dest and source path");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			System.out.println("to dest->"+destPath+file.getName());
			startFileProcessThread(file.getAbsolutePath(), realDestPath, phi, serializer, callbackserializer);
			i++;
		}	

		

		
		// Permitir a los procesadores iniciar trabajo
	}

	private String getNextSerializer() {
		if(currentSerializer>=serializers.length){
			currentSerializer=0;
		}
		
		return serializers[currentSerializer++];
	}

	private void waitWhileSerializerIsPathLocked(ISerializer serializer) {
		try {
			boolean isPathLocked = serializer.isPathLocked();
			System.out.println("is path locked: "+isPathLocked);
			while(isPathLocked){

				
				System.out.println("serializer is path locked");
				Thread.sleep(1000);
				isPathLocked = serializer.isPathLocked();
			}		
			
			System.out.println("serializer is nonPathLocked");
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
	}

	private void startFileProcessThread(final String absolutePath, final String destPath, final double phi,
			final ISerializer serializer ,final String callbackserializer) {
		Thread thread = new Thread(new Runnable(){
		
			@Override
			public void run() {
				try {
					processImage(absolutePath, destPath, phi, serializer, callbackserializer);
				} catch (Exception e) {
					//TODO: handle exception
					e.printStackTrace();
				}
			}

			
		});
		thread.start();

	}

	private ISerializer getImageSerializer(String rmiEncodedUrl) {
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

	private void processImage(String imagePath, String destPath, double phi, ISerializer serializer ,String callbackserializer) {
		int cores = 0;
		try {
			cores =  broker.getTotalProcessors();
			
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("total cores-> "+cores);
		List<Rectangle> rectangles =  tiffManager.calculateRegions(imagePath);
		System.out.println("total rectangles-> "+rectangles.size());
		try {
			serializer.setWorkers(rectangles.size());
			
			System.out.println("setted workers-> "+rectangles.size());
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		int rectanglesPerCore = rectangles.size()==1 ? 1 : rectangles.size()/cores;
		System.out.println("rectangles per core-> "+rectanglesPerCore);
		
		String[] processors=null;
		try {
			processors = broker.getTiffProcessors(Math.min(rectangles.size(), cores));	
			
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		// System.out.println("total procesors->"+processors.length);
		int currentRectangleOffset= 0;


		
		//For each processor we will process an image region.
		for (int i = 0; i < processors.length && processors!=null; i++) {

			Rectangle[] threadRectangles = new Rectangle[rectanglesPerCore];
			System.out.println("thread rectangles size-> "+threadRectangles.length);
			rectangles.subList(currentRectangleOffset, threadRectangles.length).toArray(threadRectangles);

			currentRectangleOffset+=threadRectangles.length;
			System.out.println("current offset-> "+currentRectangleOffset);
			
			runThreadProcessor(processors[i], threadRectangles, phi, callbackserializer);

		}
			
	}

	private void runThreadProcessor(final String processorStr, final Rectangle[] threadRectangles, final double phi, final String callbackserializer) {
		new Thread(new Runnable(){
		
			@Override
			public void run() {
				for (int i = 0; i < threadRectangles.length; i++) {
					final ITiffProcessor processor = getProcessor(processorStr);
					Rectangle rectangle = threadRectangles[i];
					int x = (int) rectangle.getX();
					int y = (int) rectangle.getY();
					int width = (int) rectangle.getWidth();
					int height = (int) rectangle.getHeight();
					try {
						while(processor.isLocked()){
								
								Thread.sleep(1000);
						}
					} catch (Exception e) {
							//TODO: handle exception
							e.printStackTrace();
					}
					try {
						
						processor.processSource(x, y, width, height, phi, callbackserializer);
					} catch (Exception e) {
						//TODO: handle exception
						e.printStackTrace();
					}
				}
			}

			
		}).start();

	}

	private ITiffProcessor getProcessor(String rmiEncodedUrl) {

		try {
			String[] info = rmiEncodedUrl.split(":");
			String ip = info[0];
			String port = info[1];
			String service = info[2];
			return (ITiffProcessor) Naming.lookup("rmi://"+ip+":"+port+"/"+service);
			
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	private File[] getImagesInDirectory(File directory) {
		File[] files = directory.listFiles();
		List<File> imagesFilesList = new ArrayList<File>();
		for (File file : files) {
			if(tiffManager.isImage(file)){
				System.out.println(file.getAbsolutePath()+" is an image");
				imagesFilesList.add(file);
			}else{
				System.out.println(file.getAbsolutePath()+" is not an image");
			}
		}
		File[] imageFiles = new File[imagesFilesList.size()];
		return imagesFilesList.toArray(imageFiles);
	}

	

	private File getDirectory(String sourcePath) {
		File file = new File(sourcePath);
		
		
		if(!file.isDirectory()){
			throw new IllegalArgumentException("path is not a directory");
		}
		return file;
	}

}
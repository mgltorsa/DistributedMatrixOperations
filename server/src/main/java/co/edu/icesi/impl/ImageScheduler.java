package co.edu.icesi.impl;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;


import co.edu.icesi.interfaces.IServer;
import co.edu.icesi.interfaces.IImageFileProcessor;
import co.edu.icesi.interfaces.*;
import org.osoa.sca.annotations.*;

public class ImageScheduler{

	private Queue<ImageChunk> schedule;

	private ImageProcessor imageProcessor;
	
	private IImageFileProcessor tiffProcessor;

	private IServer server;

	public ImageScheduler() {
		// TODO Auto-generated constructor stub
		schedule = new ArrayDeque<ImageChunk>();
		tiffProcessor = new TiffProcessor();
		imageProcessor = new ImageProcessor(tiffProcessor);
	}

	// public void sendImageChunkForRotation() {
	// 	imageProcessor.setImageSource("./data/source/image.jpg");
	// 	imageProcessor.setImageDestination("./data/dest/image.jpg");
	// 	imageProcessor.splitImage(1000,1000);

	// 	ImageChunk ic = imageProcessor.retriveImageChunk();
	// 	int[] init = {0,0};
	// 	int[] fin = {ic.getWidth(), ic.getHeight()};
	// 	int[][] pointsChunk = server.rotate(init, fin);

	// 	System.out.println(pointsChunk);
	// 	imageProcessor.setPoints(ic.getType(), pointsChunk);
	// 	ArrayList<ImageChunk> images = imageProcessor.getImageChunks(ic.getType());
	// 	schedule.addAll(images);
	// }
	
	

	public ImageProcessor getImageProcessor() {
		return imageProcessor;
	}

	public IImageFileProcessor getImageFileProcessor() {
		return tiffProcessor;
	}

	public void setImageProcessor(ImageProcessor imageProcessor) {
		this.imageProcessor = imageProcessor;
	}

	public void sendImageChunkForWriting() {
		
	}

	// public void test(){
	// 	sendImageChunkForRotation();
	// }

	public void setImageSource(String sourcePath) {
		// TODO Auto-generated method stub
		tiffProcessor.setSourcePath(sourcePath);
	}

	public void setImageDestination(String destPath) {
		// TODO Auto-generated method stub
		tiffProcessor.setDestinationPath(destPath);
	}

}

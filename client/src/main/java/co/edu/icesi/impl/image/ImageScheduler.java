package co.edu.icesi.impl.image;

import java.awt.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import org.oasisopen.sca.annotation.Reference;

import co.edu.icesi.interfaces.IServer;
import co.edu.icesi.interfaces.image.IImageFileProcessor;
import co.edu.icesi.interfaces.image.IImageLogicProcessor;

public class ImageScheduler implements Runnable{

	private Queue<ImageChunk> schedule;

	private IImageLogicProcessor imageProcessor;
	
	private IImageFileProcessor tiffProcessor;

	@Reference(name = "server")
	private IServer server;

	public ImageScheduler() {
		// TODO Auto-generated constructor stub
		schedule = new ArrayDeque<>();
		imageProcessor = new ImageProcessor();
		tiffProcessor = new TiffProcessor();
	}

	public void sendImageChunkForRotation() {
		ImageChunk ic = imageProcessor.retriveImageChunk();
		int[] init = {0,0};
		int[] fin = {ic.getWidth(), ic.getHeight()};
		int[][] pointsChunk = server.rotate(init, fin);
		imageProcessor.setPoints(ic.getType(), pointsChunk);
		
		ArrayList<ImageChunk> images = imageProcessor.getImageChunks(ic.getType());
		schedule.addAll(images);
	}

	public void sendImageChunkForWriting() {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		sendImageChunkForRotation();
		
	}

}

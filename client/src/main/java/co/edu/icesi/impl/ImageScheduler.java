package co.edu.icesi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;


import co.edu.icesi.interfaces.IServer;
import co.edu.icesi.interfaces.IImageFileProcessor;
import org.osoa.sca.annotations.*;

@Service(interfaces = Runnable.class)
public class ImageScheduler implements Runnable{

	private Queue<ImageChunk> schedule;

	private ImageProcessor imageProcessor;
	
	private IImageFileProcessor tiffProcessor;

	private IServer server;

	public ImageScheduler() {
		// TODO Auto-generated constructor stub
		schedule = new ArrayDeque<ImageChunk>();
		imageProcessor = new ImageProcessor();
		tiffProcessor = new TiffProcessor();
	}

	public void sendImageChunkForRotation() {
		imageProcessor.setImageSource("./data/source/image.jpg");
		imageProcessor.setImageDestination("./data/dest/image.jpg");
		imageProcessor.splitImage(1000,1000);

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

	
	@Reference(name = "server")
	public void setServer(IServer server) {
		this.server = server;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		sendImageChunkForRotation();
		
	}

}

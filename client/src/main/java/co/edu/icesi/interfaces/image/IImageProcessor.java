package co.edu.icesi.interfaces.image;

import java.util.ArrayList;
import java.util.List;

import co.edu.icesi.impl.image.ImageChunk;

public interface IImageProcessor {
	
	public ImageChunk getRemainingImageChunk();
	
	public ArrayList<ImageChunk> getImageChunks(int type);
	
	public void splitImage();

	public void splitImage(int chunkWidth, int chunkHeight);

	public ImageChunk retriveImageChunk();
	
	public void setImageChunkProcessed(int[][] points, ImageChunk chunk);

	public void setPoints(int type, int[][] pointsChunk);

}

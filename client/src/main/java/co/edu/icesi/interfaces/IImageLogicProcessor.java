package co.edu.icesi.interfaces;

import java.util.ArrayList;

import co.edu.icesi.ImageChunk;

public interface IImageLogicProcessor {
	
	public ArrayList<ImageChunk> getImageChunks(int type);
	
	public void splitImage();

	public void splitImage(int chunkWidth, int chunkHeight);

	public ImageChunk retriveImageChunk();
	
	public void setImageChunkProcessed(int[][] points, ImageChunk chunk);

	public void setPoints(int type, int[][] pointsChunk);

}

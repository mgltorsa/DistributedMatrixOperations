package co.edu.icesi.interfaces;

import co.edu.icesi.implementation.ImageChunk;

public interface IImageProcessor {
	
	public ImageChunk getRemainingImageChunk();
	
	public void splitImage();

	public void splitImage(int chunkWidth, int chunkHeight);

	public ImageChunk retriveImageChunk();
	
	public void setImageChunkProcessed(int[][] points, ImageChunk chunk);

}

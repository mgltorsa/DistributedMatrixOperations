package co.edu.icesi.interfaces;

import java.util.ArrayList;

import co.edu.icesi.impl.ImageChunk;

public interface IImageLogicProcessor {
	
	public ArrayList<ImageChunk> getImageChunks(int type);
	
	public void splitImage();

	public void splitImage(int chunkWidth, int chunkHeight);

	public ImageChunk retriveImageChunk();
	
	public void setPoints(int type, int[][] pointsChunk);

}

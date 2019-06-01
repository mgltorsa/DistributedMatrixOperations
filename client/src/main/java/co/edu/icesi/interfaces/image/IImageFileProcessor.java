package co.edu.icesi.interfaces.image;

import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;

import co.edu.icesi.impl.image.ImageChunk;

public interface IImageFileProcessor {

	
	public BufferedImage imageChunk(int x, int y, int height, int width, String path);

	public void saveImageChunk(int[][] points, ImageChunk imageChunk);
	
	public void writeImageChunk(BufferedImage bufferChunk);
	
	public void setImageProperties();

	public void setSourcePath(String source);

	public void setDestinationPath(String destination);

	public int getImageHeight();

	public int getImageWidth();
	
}

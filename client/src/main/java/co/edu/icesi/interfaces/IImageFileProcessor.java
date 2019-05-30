package co.edu.icesi.interfaces;

import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;

import co.edu.icesi.implementation.ImageChunk;

public interface IImageFileProcessor {

	public BufferedImage imageChunk(int x, int y, int height, int width);

	public void saveImageChunk(int[][] points, ImageChunk imageChunk);
	
	public void defineImageCut();

	public void setImageProperties();

	public void setSourcePath(String source);

	public void setDestinationPath(String destination);

	public int getImageHeight();

	public int getImageWidth();
}

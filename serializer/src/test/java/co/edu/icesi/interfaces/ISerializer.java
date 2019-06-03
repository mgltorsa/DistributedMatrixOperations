package test.java.co.edu.icesi.interfaces;

import java.awt.image.BufferedImage;

/**
 * ISerializer
 */
public interface ISerializer {

	public void setWorkers(int workers);
	
	public void setDestPath(String path);
	
	public boolean isLocked();
	
	public boolean drawImage(int x, int y, int width, int height, int[][] image);
	
}
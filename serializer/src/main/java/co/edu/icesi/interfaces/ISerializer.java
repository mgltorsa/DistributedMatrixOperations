package co.edu.icesi.interfaces;

/**
 * ISerializer
 */
public interface ISerializer {

    public void setWorkers(int workers);
	
    public void setDestPath(String path);
    
    public void setSourcePath(String path);
	
	public boolean isLocked();
	
    public void drawImage(int x, int y, int width, int height, int[][] image);
}
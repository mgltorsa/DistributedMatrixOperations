package co.edu.icesi;

import co.edu.icesi.implementation.ImageChunk;
import co.edu.icesi.implementation.ImageProcessor;

public class App {

	public static void main(String[] args) {
		double sin = Math.sin(Math.PI / 4.0);
		double cos = Math.cos(Math.PI / 4.0);

		ImageProcessor ip = new ImageProcessor();

		ip.setImageDestination("./data/dest/image.jpg");
		ip.setImageSource("./data/source/image.jpg");

		ip.splitImage(500,500);

		ImageChunk ic = ip.getRemainingImageChunk();

		int mx = 0, Mx = 0, my = 0, My = 0;

		int arraySize = ic.getHeight() * ic.getWidth();
		
		int[][] points = new int[2][arraySize + 2];

		for (int j = 0; j < points.length; j++) {
			
			int i = j%ic.getWidth();
			
			points[0][i] = (int) (cos * (i) + (-sin * j));
			points[1][j] = (int) (cos * (j) + (sin * i));
			
			if(points[i][j] < mx)
				mx = points[i][j];
			if(points[i][j] > Mx)
				Mx = points[i][j];
			if(points[i][j] < my)
				my = points[i][j];
			if(points[i][j] < My)
				My = points[i][j];
		}
		
		points[0][arraySize] = mx;
		points[1][arraySize] = Mx;
		
		points[0][arraySize+1] = mx;
		points[1][arraySize+1] = Mx;
		
		ip.setImageChunkProcessed(points, ic);

	}
}
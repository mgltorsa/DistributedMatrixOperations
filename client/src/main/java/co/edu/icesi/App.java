package co.edu.icesi;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import co.edu.icesi.impl.image.ImageChunk;
import co.edu.icesi.impl.image.ImageProcessor;

public class App {

	public static void main(String[] args) {
		double sin = Math.sin((Math.PI / 3.0));
		double cos = Math.cos((Math.PI / 3.0));

		ImageProcessor ip = new ImageProcessor();

		ip.setImageDestination("./data/dest/test3.jpg");
		ip.setImageSource("./data/source/image.jpg");

		ip.splitImage(4320,4320);
		
		ImageChunk ic = ip.retriveImageChunk();
		//--------------------------------------------------------------------------------------------------------------
		
		try {
			File image = new File("./data/source/image.jpg");
		
		//--------------------------------------------------------------------------------------------------------------


		int mx = Integer.MAX_VALUE, Mx = Integer.MIN_VALUE, my = Integer.MAX_VALUE, My = Integer.MIN_VALUE;

		int arraySize = ic.getHeight() * ic.getWidth();
		
		int[][] points = new int[2][arraySize + 2];

		for (int j = 0; j < points[0].length; j++) {
			
			double w = ic.getWidth();
			int x = j%ic.getWidth();
			int y = (int) ((j/w)%ic.getHeight());
			
			int xp = (int) (cos * (x) - (sin * y));
			int yp = (int) (cos * (y) + (sin * x));
			points[0][j] = xp;
			points[1][j] = yp;
			
			if(xp < mx) {
				mx = points[0][j];}
			if(xp > Mx)
				Mx = points[0][j];
			if(yp < my)
				my = points[1][j];
			if(yp > My)
				My = points[1][j];
		}
		
//		System.out.println(my);
		points[0][arraySize] = mx;
		points[1][arraySize] = Mx;
		
		points[0][arraySize+1] = my;
		points[1][arraySize+1] = My;
		
		ip.setImageChunkProcessed(points, ic);

	}
}
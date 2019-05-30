package co.edu.icesi.implementation;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.w3c.dom.css.RGBColor;

import co.edu.icesi.interfaces.IImageFileProcessor;

public class TiffProcessor implements IImageFileProcessor {

	private String sourcePath;

	private String destinationPath;

	private int width;

	private int height;

	public TiffProcessor() {
		this.sourcePath = "";
		this.destinationPath = "";
	}

	public TiffProcessor(String sourcePath, String destinationPath) {
		this.sourcePath = sourcePath;
		this.destinationPath = destinationPath;
	}

	@Override
	public BufferedImage imageChunk(int x, int y, int height, int width) {

		try {
			File image = new File(sourcePath);
			ImageInputStream iis = ImageIO.createImageInputStream(image);

			ImageReader r = ImageIO.getImageReaders(iis).next();

			r.setInput(iis);

			ImageReadParam param = r.getDefaultReadParam();
			param.setSourceRegion(new Rectangle(x, y, height, width));
			BufferedImage bi = r.read(0, param);

			return bi;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void setImageProperties() {
		try {
			File image = new File(sourcePath);
			ImageInputStream iis = ImageIO.createImageInputStream(image);

			ImageReader r = ImageIO.getImageReaders(iis).next();

			r.setInput(iis);

			height = r.getHeight(0);
			width = r.getWidth(0);

			System.out.println(height);
			System.out.println(width);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	@Override
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	@Override
	public int getImageHeight() {
		return height;
	}

	@Override
	public int getImageWidth() {
		return width;
	}

	@Override
	public void saveImageChunk(int[][] points, ImageChunk imageChunk) {
		
		//Last index in the arrays of points
		int lastIndex = imageChunk.getHeight() * imageChunk.getWidth();
		
		//-------------------------------------------------------------
		// Image chunk information
		//-------------------------------------------------------------
		int height = imageChunk.getHeight();
		int width = imageChunk.getWidth();
		
		//Original starting point in the whole image
		int originalX = (int) (imageChunk.getPoint().getX() * imageChunk.getWidth());
		int originalY = (int) (imageChunk.getPoint().getY() * imageChunk.getHeight());

		//Original top-left point rotated
		int localDeltaX = points[imageChunk.getWidth() - 1][2];
		int localDeltaY = points[imageChunk.getWidth() - 1][3];

		//Chunk corner points rotated
		int xleft = 0, xright = 0, ytop = 0, ybottom = 0;

		xleft = points[lastIndex][0];
		xright = points[lastIndex][1];
		ytop = points[lastIndex][2];
		ybottom = points[lastIndex][3];

		//Require size to contain the chunk rotated without losing bytes
		int deltaWidth = Math.abs(xleft - xright), deltaHeight = Math.abs(ytop - ybottom);

		//-------------------------------------------------------------
		// Original image information
		//-------------------------------------------------------------
		
		//Image corner points rotated
		int imageTopY = 0, imageBottomY = 0, imageLetfX = 0, imageRightX = 0;
		
		int fitBufferX = imageTopY;
		int fitBufferY = imageLetfX;
		
		
		// Original image chunk
		BufferedImage originalChunk = imageChunk(originalX, originalY, height, width);
		
		// New image to save the rotated chunk image
		BufferedImage fitChunk = imageChunk(originalX, originalY+imageTopY-localDeltaY, deltaWidth, deltaHeight);
		
		
		for(int i = 0; i < points.length; i++) {
			int oldPoint = originalChunk.getRGB(points[i][0], points[i][1]);
			
			int newX = points[i][2]-(localDeltaX < 0 ? localDeltaX:0);
			int newY = points[i][3]-(localDeltaY > 0 ? localDeltaY:0);
			
			fitChunk.setRGB(newX, newY, oldPoint);
			
		}

	}

	@Override
	public void defineImageCut() {

	}

}

package co.edu.icesi;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;


import co.edu.icesi.interfaces.IImageFileProcessor;

public class TiffProcessor implements IImageFileProcessor {

	private String sourcePath;

	private String destinationPath;

	private Point topLeft;

	private Point topRight;

	private Point bottomLeft;

	private Point bottomRight;

	private double degree;

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
	public BufferedImage imageChunk(int x, int y, int height, int width, String path) {

		try {
			File image = new File(path);
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
		public void saveImageChunk(int[][] points, ImageChunk imageChunk) {
	
			// Last index in the arrays of points
			int lastIndex = imageChunk.getHeight() * imageChunk.getWidth();
	
			// -------------------------------------------------------------
			// Image chunk information
			// -------------------------------------------------------------
			int height = imageChunk.getHeight();
			int width = imageChunk.getWidth();
	
			// Original starting point in the whole image
			int originalX = (int) (imageChunk.getPoint().getX() * imageChunk.getWidth());
			int originalY = (int) (imageChunk.getPoint().getY() * imageChunk.getHeight());
	
			// Original top-left point rotated
			int localDeltaX = points[0][imageChunk.getWidth() - 1];
			int localDeltaY = points[1][imageChunk.getWidth() - 1];
	
			System.out.println(localDeltaX);
			System.out.println(localDeltaY);
	
			// Chunk corner points rotated
			int xleft = 0, xright = 0, ytop = 0, ybottom = 0;
	
			xleft = points[0][lastIndex];
			xright = points[1][lastIndex];
			ybottom = points[0][lastIndex + 1];
			ytop = points[1][lastIndex + 1];
	
			// Require size to contain the chunk rotated without losing bytes
			int deltaWidth = Math.abs(xleft - xright) + 1, deltaHeight = Math.abs(ytop - ybottom) + 1;
	
			// -------------------------------------------------------------
			// Original image information
			// -------------------------------------------------------------
	
			// Image corner points rotated
			int imageTopY = 0, imageBottomY = 0, imageLetfX = 0, imageRightX = 0;
	
			int fitBufferX = imageTopY;
			int fitBufferY = imageLetfX;
	
			int leftX = (int) Math.min(0, Math.min(topRight.getX(), Math.min(bottomRight.getX(), bottomLeft.getX())));
			int rightX = (int) Math.max(0, Math.max(topRight.getX(), Math.max(bottomRight.getX(), bottomLeft.getX())));
			int bottomY = (int) Math.min(0, Math.min(topRight.getY(), Math.min(bottomRight.getY(), bottomLeft.getY())));
			int topY = (int) Math.max(0, Math.max(topRight.getY(), Math.max(bottomRight.getY(), bottomLeft.getY())));
	
			// Original image chunk
			BufferedImage originalChunk = imageChunk(originalX, originalY, height, width, sourcePath);
	
			// New image to save the rotated chunk image
	//		BufferedImage originalChunk = imageChunk(originalX, originalY, height, width, destinationPath);
			BufferedImage bi = new BufferedImage(deltaWidth, deltaHeight, BufferedImage.TYPE_INT_RGB);
			
			int[][] newImage = new int[deltaWidth][deltaHeight];
	
			for (int i = 0; i < lastIndex; i++) {
	//			int oldPoint = originalChunk.getRGB(points[0][i], points[1][i]);
	
				int newX = points[0][i] - (xleft < 0 ? xleft : 0);
				int newY = points[1][i] - (ybottom < 0 ? ybottom : 0);
	
				double w = imageChunk.getWidth();
				int x = i%imageChunk.getWidth();
				int y = (int) ((i/w)%imageChunk.getHeight());
				
				bi.setRGB(newX, newY, originalChunk.getRGB(x, y));
			}
		}

	@Override
	public void writeImageChunk(BufferedImage bufferChunk) {
		// TODO Auto-generated method stub
		try {
			File f = new  File(destinationPath);
			FileImageOutputStream output = new FileImageOutputStream(f);
			
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
			ImageWriter writer = writers.next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			
			writer.setOutput(output);
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			IIOImage image = new IIOImage(bufferChunk, null, null);
			
			writer.write(null, image, param);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

			double sin = Math.sin(degree);
			double cos = Math.cos(degree);

			int topRightX = (int) (cos * 0 + (-sin * (width)));
			int topRightY = (int) (cos * (width) + sin * (0));

			int bottomRightX = (int) (cos * (height) * +(-sin * (width)));
			int bottomRightY = (int) (cos * (width) + sin * (height));

			int bottomLeftX = (int) (cos * (height) * +(-sin * 0));
			int bottomLeftY = (int) (cos * 0 + sin * (height));

			topLeft = new Point(0, 0);
			topRight = new Point(topRightX, topRightY);
			bottomLeft = new Point(bottomLeftX, bottomLeftY);
			bottomRight = new Point(bottomRightX, bottomRightY);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
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

	public String getSourcePath() {
		return sourcePath;
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

}

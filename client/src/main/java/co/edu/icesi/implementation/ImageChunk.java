package co.edu.icesi.implementation;

import java.awt.Point;

public class ImageChunk {
	
	public final static int DEFAUL_SIZE = 5000;

	private Point point;
	
	private int width;
	
	private int height;
	
	public ImageChunk(int height, int width) {
		// TODO Auto-generated constructor stub
		this.height = height;
		this.width = width;
	}
	
	public ImageChunk() {
		// TODO Auto-generated constructor stub
		this.width = DEFAUL_SIZE;
		this.height = DEFAUL_SIZE;
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
	
	public void setPoint(Point point){
		this.point = point;
	}

	public Point getPoint(){
		return point;
	}
	
}

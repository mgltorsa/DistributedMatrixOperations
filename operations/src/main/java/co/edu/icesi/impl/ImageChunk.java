<<<<<<< HEAD:client/src/main/java/co/edu/icesi/implementation/ImageChunk.java
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
=======
package co.edu.icesi.impl;
import java.awt.Point;

public class ImageChunk {
	
	public final static int FULL_ABSOLUTE = 1;
	
	public final static int FULL_WIDTH = 2;
	
	public final static int FULL_HEIGHT = 3;
	
	public final static int FULL_MOD = 4;
	
	public final static int DEFAUL_SIZE = 5000;
	
	private Point point;

	private int type;
	
	private int width;
	
	private int height;
	
	public ImageChunk(int height, int width){
		// TODO Auto-generated constructor stub
		this.type = FULL_ABSOLUTE;
		this.height = height;
		this.width = width;
	}
	
	public ImageChunk(){
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

	public void setType(int fullMod) {
		// TODO Auto-generated method stub
		
	}

	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}

	
}
>>>>>>> d4755263fc315e725c44e61eecea6eef0e37914e:operations/src/main/java/co/edu/icesi/impl/ImageChunk.java

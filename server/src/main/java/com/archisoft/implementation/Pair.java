package com.archisoft.implementation;

public class Pair {
	
	private int x;
	
	private int y;
	
	private int xo;
	
	private int yo;
	
	public Pair(int x, int y , int xo, int yo) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.xo = xo;
		this.yo = yo;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int getXo() {
		return xo;
	}

	public void setXo(int xo) {
		this.xo = xo;
	}

	public int getYo() {
		return yo;
	}

	public void setYo(int yo) {
		this.yo = yo;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int[] getVector() {
		int[] vector = {this.x, this.y};
		return vector;
	}

}

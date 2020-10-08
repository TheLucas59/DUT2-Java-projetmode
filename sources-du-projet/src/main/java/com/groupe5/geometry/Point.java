package com.groupe5.geometry;

public class Point {
	private float x;
	private float y;
	private float z;
	public int id;
	
	public Point(float x, float y, float z, int id) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	public String toString() {
		return this.x + "," + this.y + "," + this.z;
	}
}

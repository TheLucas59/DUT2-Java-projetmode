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

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
	
	public String toString() {
		return this.x + "," + this.y + "," + this.z;
	}

	public void setX(float f) {
		this.x = f;		
	}

	public void setY(float f) {
		this.y = f;
	}
	
	public void setZ(float f) {
		this.z = f;
	}
}

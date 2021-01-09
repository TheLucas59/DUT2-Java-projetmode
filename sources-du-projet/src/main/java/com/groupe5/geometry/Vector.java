package com.groupe5.geometry;

public class Vector {

	private float x;
	private float y;
	private float z;
	
	public Vector(Point p1, Point p2) {		
		x = p2.getX() - p1.getX();
		y = p2.getY() - p1.getY();
		z = p2.getZ() - p1.getZ();
	}
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float[] getVector() {
		return new float[] {x, y, z};
	}
	
	public Vector produitVectoriel(Vector other) {
		float newx = this.y * other.z - other.y * this.z;
		float newy = this.z * other.x - other.z * this.x;
		float newz = this.x * other.y - other.x * this.y;
		return new Vector(newx, newy, newz);
	}
	
	public float produitScalaire(Vector other) {
		float newx = this.x * other.x;
		float newy = this.y * other.y;
		float newz = this.z * other.z;
		return newx + newy + newz;
	}

	public float norme() {
		return (float) Math.sqrt(Math.pow(this.getX(), 2.0) + Math.pow(this.getY(), 2.0) + Math.pow(this.getZ(), 2.0));
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

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public String toString() {
		return x + " " + y + " " + z;
	}
}

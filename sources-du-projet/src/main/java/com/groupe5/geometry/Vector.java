package com.groupe5.geometry;

public class Vector {

	private float x;
	private float y;
	private float z;
	
	public Vector(Point p1, Point p2) {		
		x = p1.getX() - p2.getX();
		y = p1.getY() - p2.getY();
		z = p1.getZ() - p2.getZ();
		
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
		float newx = this.x * other.y;
		float newy = this.y * other.y;
		float newz = this.z * other.z;
		return newx + newy + newz;
	}
	
}

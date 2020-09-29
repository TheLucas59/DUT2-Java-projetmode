package com.groupe5.geometry;

public class Face {
	private int nbPoints;
	private Point[] points;
	
	public Face(int nbPoints, Point[] points) {
		this.points = points;
		this.nbPoints = points.length;
	}
	
	public int getNbPoints() {
		return nbPoints;
	}
	
}

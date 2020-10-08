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

	public Point[] getPoints() {
		return points;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(Point p : points) {
			sb.append("(").append(p).append(")");
		}
		sb.append("]");
		return sb.toString();
	}
}

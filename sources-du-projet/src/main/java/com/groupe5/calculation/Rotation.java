package com.groupe5.calculation;

public class Rotation {

	Matrix rotation;
	double teta;
	
	public Rotation(double teta) {
		
		this.teta = Math.toRadians(teta);
		
		rotation = new Matrix(new double[] {Math.cos(teta), -Math.sin(teta), 0, 0}, 
						 new double[] {Math.sin(teta), Math.cos(teta), 0, 0}, 
						 new double[] {0, 0, 1, 0},
						 new double[] {0, 0, 0, 1});
		
	}
	
	public Matrix getRotation() {
		return rotation;
	}
	
}

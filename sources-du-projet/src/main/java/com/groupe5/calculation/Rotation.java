package com.groupe5.calculation;

public class Rotation {

	Matrix rotation;
	double teta;
	
	public Rotation(double teta) {
		
		this.teta = Math.toRadians(teta);
		
		rotation = new Matrix(new float[] {(float) Math.cos(teta), (float) -Math.sin(teta), 0, 0}, 
						 new float[] {(float) Math.sin(teta), (float) Math.cos(teta), 0, 0}, 
						 new float[] {0, 0, 1, 0},
						 new float[] {0, 0, 0, 1});
		
	}
	
}

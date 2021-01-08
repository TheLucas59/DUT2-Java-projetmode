package com.groupe5.calculation;

public class RotationZ extends Matrix{
	
	public RotationZ(float teta) {
		
		super(
			new float[][] {
				{(float) Math.cos(Math.toRadians(teta)), (float) -Math.sin(Math.toRadians(teta)), 0, 0},
				{(float) Math.sin(Math.toRadians(teta)), (float) Math.cos(Math.toRadians(teta)), 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
			}
		);		
	}
	
	public float[][] getRotation() {
		return super.getMatrix();
	}
	
}

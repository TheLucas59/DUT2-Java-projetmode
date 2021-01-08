package com.groupe5.calculation;

public class RotationY extends Matrix {
	public RotationY(float teta) {
			
			super(
				new float[][] {
					{(float) Math.cos(Math.toRadians(teta)), 0, (float) -Math.sin(Math.toRadians(teta)), 0},
					{0, 1, 0, 0},
					{(float) Math.sin(Math.toRadians(teta)), 0, (float) Math.cos(Math.toRadians(teta)), 0},
					{0, 0, 0, 1}
				}
			);		
		}
		
	public float[][] getRotation() {
		return super.getMatrix();
	}
}

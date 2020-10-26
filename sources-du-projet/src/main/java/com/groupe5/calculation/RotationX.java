package com.groupe5.calculation;

public class RotationX extends Matrix {
	public RotationX(float teta) {
			
			super(
				new float[][] {
					{1, 0, 0, 0},
					{0, (float) Math.cos(Math.toRadians(teta)), (float) -Math.sin(Math.toRadians(teta)), 0},
					{0, (float) Math.sin(Math.toRadians(teta)), (float) Math.cos(Math.toRadians(teta)), 0},
					{0, 0, 0, 1}
				}
			);		
		}
		
	public float[][] getRotation() {
		return super.getMatrix();
	}
}

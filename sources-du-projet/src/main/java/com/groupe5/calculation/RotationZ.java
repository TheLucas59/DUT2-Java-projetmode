package com.groupe5.calculation;

public class RotationZ extends Matrix{
	
	public RotationZ(float teta) {
		
		super(
			new float[][] {
				{(float) Math.cos(teta), (float) -Math.sin(teta), 0, 0},
				{(float) Math.sin(teta), (float) Math.cos(teta), 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
			}
		);		
	}
	
	public float[][] getRotation() {
		return super.getMatrix();
	}
	
}

package com.groupe5.calculation;

public class Rotation extends Matrix {
	
	public Rotation(float teta) {

		//AUTOUR DE L'AXE Z

		super(new float[] {teta, teta, 0, 0},
				new float[] {teta, teta, 0, 0},
				new float[] {0, 0, 1, 0},
				new float[] {0, 0, 0, 1});

		//AUTOUR DE L'AXE X

		/*
		super(new float[] {1, 0, 0, 0},
				new float[] {0, Math.cos(Math.toRadians(teta)), -Math.sin(Math.toRadians(teta)), 0},
				new float[] {0, Math.sin(Math.toRadians(teta)), Math.cos(Math.toRadians(teta)), 0},
				new float[] {0, 0, 0, 1});

		//AUTOUR DE L'AXE Y

		super(new float[] {Math.cos(Math.toRadians(teta)), 0, Math.sin(Math.toRadians(teta)), 0},
				new float[] {0, 1, 0, 0},
				new float[] {-Math.sin(Math.toRadians(teta)), 0, Math.cos(Math.toRadians(teta)), 0},
				new float[] {0, 0, 0, 1});
		 */
		
	}
	
	public float[][] getRotation() {
		return super.getMatrix();
	}
	
}

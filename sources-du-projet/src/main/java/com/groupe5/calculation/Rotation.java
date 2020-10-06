package com.groupe5.calculation;

public class Rotation extends Matrix {
	
	public Rotation(double teta) {

		//AUTOUR DE L'AXE Z

		super(new double[] {Math.cos(Math.toRadians(teta)), -Math.sin(Math.toRadians(teta)), 0, 0},
				new double[] {Math.sin(Math.toRadians(teta)), Math.cos(Math.toRadians(teta)), 0, 0},
				new double[] {0, 0, 1, 0},
				new double[] {0, 0, 0, 1});

		//AUTOUR DE L'AXE X

		/*
		super(new double[] {1, 0, 0, 0},
				new double[] {0, Math.cos(Math.toRadians(teta)), -Math.sin(Math.toRadians(teta)), 0},
				new double[] {0, Math.sin(Math.toRadians(teta)), Math.cos(Math.toRadians(teta)), 0},
				new double[] {0, 0, 0, 1});

		//AUTOUR DE L'AXE Y

		super(new double[] {Math.cos(Math.toRadians(teta)), 0, Math.sin(Math.toRadians(teta)), 0},
				new double[] {0, 1, 0, 0},
				new double[] {-Math.sin(Math.toRadians(teta)), 0, Math.cos(Math.toRadians(teta)), 0},
				new double[] {0, 0, 0, 1});
		 */
		
	}
	
	public double[][] getRotation() {
		return super.getMatrix();
	}
	
}

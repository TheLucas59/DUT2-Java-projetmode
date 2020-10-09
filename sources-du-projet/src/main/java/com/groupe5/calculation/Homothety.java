package com.groupe5.calculation;

//CLASS ZOOM

public class Homothety extends Matrix {

	public Homothety(int coeff) {

		super(new float[] {coeff, 0, 0, 0},
			  new float[] {0, coeff, 0, 0},
			  new float[] {0, 0, coeff, 0},
			  new float[] {0, 0, 0, 1});
		
	}

	public float[][] getHomothety() {
		return super.getMatrix();
	}	
}

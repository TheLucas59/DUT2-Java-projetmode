package com.groupe5.calculation;

//CLASS ZOOM

public class Homothety extends Matrix {
	
	public Homothety(double coeff) {				
		super(
			new float[][] {
			 	{(float) coeff, 0, 0, 0},
			 	{0, (float) coeff, 0, 0},
			 	{0, 0, (float) coeff, 0},
			 	{0, 0, 0, 1}
			});
	}

	public float[][] getHomothety() {
		return super.getMatrix();
	}	
}

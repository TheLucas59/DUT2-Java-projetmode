package com.groupe5.calculation;

//CLASS ZOOM

public class Homothety extends Matrix {

	public Homothety(int coeff) {

		super(new double[] {coeff, 0, 0, 0},
			  new double[] {0, coeff, 0, 0},
			  new double[] {0, 0, coeff, 0},
			  new double[] {0, 0, 0, 1});
		
	}

	public double[][] getHomothety() {
		return super.getMatrix();
	}	
}

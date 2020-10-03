package com.groupe5.calculation;

//CLASS ZOOM

public class Homothety {

	private Matrix homothety;
	
	public Homothety(int coefficient) {
		
		homothety = new Matrix(new double[] {coefficient, 0, 0, 0},
							   new double[] {0, coefficient, 0, 0},
							   new double[] {0, 0, coefficient, 0},
							   new double[] {0, 0, 0, 1});
		
	}

	public Matrix getHomothety() {
		return homothety;
	}	
}

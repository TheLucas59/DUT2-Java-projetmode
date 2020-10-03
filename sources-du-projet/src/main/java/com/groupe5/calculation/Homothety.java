package com.groupe5.calculation;

//CLASS ZOOM

public class Homothety {

	private Matrix homothety;
	
	public Homothety() {
		
		homothety = new Matrix(new double[] {},
							   new double[] {},
							   new double[] {},
							   new double[] {});
		
	}

	public Matrix getHomothety() {
		return homothety;
	}	
}

package com.groupe5.calculation;

//CLASS ZOOM

public class Homothety {

	private Matrix homothety;
	
	public Homothety() {
		
		homothety = new Matrix(line1, 
							   line2, 
							   line3, 
							   line4);
		
	}

	public Matrix getHomothety() {
		return homothety;
	}	
}

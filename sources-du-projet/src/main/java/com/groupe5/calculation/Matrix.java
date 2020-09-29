package com.groupe5.calculation;

public class Matrix {
	
	private double[][] matrix;

	public Matrix(double[] line1, double[] line2, double[] line3, double[] line4) {
		matrix = new double[][] {line1, line2, line3, line4};
	}
	
	public double[][] getMatrix(){
		return matrix;
	}
}

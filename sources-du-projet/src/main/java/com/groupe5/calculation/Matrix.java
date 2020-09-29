package com.groupe5.calculation;

public class Matrix {
	
	private float[][] matrix;

	public Matrix(float[] line1, float[] line2, float[] line3, float[] line4) {
		matrix = new float[][] {line1, line2, line3, line4};
	}
	
	public float[][] getMatrix(){
		return matrix;
	}
}

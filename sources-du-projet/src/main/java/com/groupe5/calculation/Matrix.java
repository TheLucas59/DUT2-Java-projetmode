package com.groupe5.calculation;

public class Matrix {
	
	private float[][] matrix;

	public Matrix(float[] line1, float[] line2, float[] line3, float[] line4) {
		matrix = new float[][] {line1, line2, line3, line4};
	}
	
	public float[][] getMatrix() {
		return matrix;
	}

	public Matrix multiply(Matrix other) {

		int size = getMatrix().length;

		float[][] result = new float[size][size];

		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				result[i][j] = 0;
				for(int k=0; k<size; k++) {
					result[i][j] += this.getMatrix()[i][k] * other.getMatrix()[k][j];
				}
			}
		}

		return new Matrix(result[0], result[1], result[2], result[3]);
	}

	public String toString() {
		String ret = " ╭-----------------------╮ \n";

		for(int i=0; i<getMatrix().length; i++) {
			for(int j=0; j<getMatrix().length; j++) {
				ret += " | " + matrix[i][j];
				if(j==getMatrix().length-1) ret += " |\n";
			}
		}

		ret += " ╰-----------------------╯";

		return ret;
	}
}

package com.groupe5.calculation;

public class Matrix {
	
	private double[][] matrix;

	public Matrix(double[] line1, double[] line2, double[] line3, double[] line4) {
		matrix = new double[][] {line1, line2, line3, line4};
	}
	
	public double[][] getMatrix() {
		return matrix;
	}

	public Matrix multiply(Matrix other) {

		int size = getMatrix().length;

		double[][] result = new double[size][size];

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

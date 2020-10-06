package com.groupe5.calculation;

public class Matrix {
	
	private double[][] matrix;

	public Matrix(double[] line1, double[] line2, double[] line3, double[] line4) {
		matrix = new double[][] {line1, line2, line3, line4};
	}
	
	public double[][] getMatrix(){
		return matrix;
	}

	public String toString() {
		String ret = "";

		for(int i=0; i<getMatrix().length; i++) {
			for(int j=0; j<getMatrix().length; j++) {
				ret += matrix[i][j] + " ";
			}
			ret += "\n";
		}

		return ret;
	}

	public static void main(String[] args) {
		Homothety m = new Homothety(2);
		System.out.println(m.toString());
	}
}

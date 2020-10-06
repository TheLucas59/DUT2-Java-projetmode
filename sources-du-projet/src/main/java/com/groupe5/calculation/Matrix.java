package com.groupe5.calculation;

import com.groupe5.geometry.Point;
import com.groupe5.geometry.Vector;

public class Matrix {
	
	private double[][] matrix;

	public Matrix(double[] line1, double[] line2, double[] line3, double[] line4) {
		matrix = new double[][] {line1, line2, line3, line4};
	}
	
	public double[][] getMatrix() {
		return matrix;
	}

	public Matrix multiply(Matrix other) {
		return null;
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

	public static void main(String[] args) {
		Homothety h = new Homothety(2);
		Rotation r = new Rotation(60);
		Translation t = new Translation(new Vector(new Point(0, 0, 0), new Point(5, 4, 3)));

		System.out.println(h);
		System.out.println(r);
		System.out.println(t);

		System.out.println(h.multiply(t).toString());
	}
}

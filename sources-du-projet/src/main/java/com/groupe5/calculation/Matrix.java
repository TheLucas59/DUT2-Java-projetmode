package com.groupe5.calculation;

import java.util.ArrayList;

import com.groupe5.geometry.Point;
import com.groupe5.view.Viewer;

public class Matrix {
	
	private float[][] matrix;
	
	private final float COORDONNEE_HOMOGENE = 1.0f;

	public Matrix(ArrayList<Point> points) {
		matrix = new float[points.size()][4];
		
		for(int i=0; i<matrix.length; i++) {
			for(int j=0; j<4; j++) {
				
				if(j == 0) matrix[i][j] = points.get(i).getX();
				if(j == 1) matrix[i][j] = points.get(i).getY();
				if(j == 2) matrix[i][j] = points.get(i).getZ();
				if(j == 3) matrix[i][j] = COORDONNEE_HOMOGENE;
						
			}
		}
	}
	
	public Matrix(float[][] matrix) {
		this.matrix = matrix;
	}
	
	public float[][] getMatrix() {
		return matrix;
	}
	
	public double[] getLineX() {
		double[] x = new double[matrix.length];
		
		for(int i=0; i<x.length; i++) {
			x[i] = matrix[i][0] + Viewer.widthCanvas/2;
		}
		
		return x;
	}
	
	public double[] getLineY() {
		double[] y = new double[matrix.length];
		
		for(int i=0; i<y.length; i++) {
			y[i] = matrix[i][1] + Viewer.heightCanvas/1.25;
		}
		
		return y;
	}
	
	public double[] getLineZ() {
		double[] z = new double[matrix.length];
		
		for(int i=0; i<z.length; i++) {
			z[i] = matrix[i][2];
		}
		
		return z;
	}

	public float[][] multiply(Matrix other) {
		
		float[][] result = new float[matrix.length][matrix[1].length];

		for(int i=0; i<matrix.length; i++) {
			for(int j=0; j<matrix[i].length; j++) {
				result[i][j] = 0;
				for(int k=0; k<matrix[i].length; k++) {
					result[i][j] += this.getMatrix()[i][k] * other.getMatrix()[k][j];
				}
			}
		}	

		return result;
	}
	
	public void setMatrix(float[][] tab) {
		matrix = tab;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder("");
		
		for(int i=0; i<matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				ret.append(matrix[i][j] + " ");
			}
			ret.append("\n");
		}

		return ret.toString();
	}
}

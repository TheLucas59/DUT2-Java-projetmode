package com.groupe5.calculation;

import java.util.ArrayList;

import com.groupe5.geometry.Point;

public class Matrix {
	
	private float[][] matrix;
	
	private final float COORDONNEE_HOMOGENE = 1.0f;

	public Matrix(ArrayList<Point> points) {
		
		matrix = new float[4][points.size()];			
		
		for(int i=0; i<matrix.length; i++) {
			for(int j=0; j<matrix[i].length; j++) {
				
				if(i == 0) matrix[i][j] = points.get(j).getX();
				if(i == 1) matrix[i][j] = points.get(j).getY();
				if(i == 2) matrix[i][j] = points.get(j).getZ();
				if(i == 3) matrix[i][j] = COORDONNEE_HOMOGENE;
						
			}
		}
	}
	
	public Matrix(float[][] matrix) {
		this.matrix = matrix;
	}
	
	public float[][] getMatrix() {
		return matrix;
	}
	
	public double[] getColumn(int indice) {
		double[] coords = new double[matrix.length-1];
		for(int i = 0; i < matrix.length-1; i++) {
			coords[i] = (double) matrix[i][indice];
		}
		return coords;
		
	}
	public double[] getLineX() {
		double[] x = new double[matrix[0].length];		
		
		for(int i=0; i<x.length; i++) {
			x[i] = matrix[0][i];
		}
		
		return x;
	}
	
	public double[] getLineY() {
		double[] y = new double[matrix[0].length];
		
		for(int i=0; i<y.length; i++) {
			y[i] = matrix[1][i];
		}
		
		return y;
	}
	
	public double[] getLineZ() {
		double[] z = new double[matrix[0].length];
		
		for(int i=0; i<z.length; i++) {
			z[i] = matrix[2][i];
		}
		
		return z;
	}
	
	public float[][] multiply(float[][] secondMatrix) {
		float[][] firstMatrix = this.getMatrix();
		
        float[][] product = new float[firstMatrix.length][secondMatrix[0].length];
        for(int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < secondMatrix[i].length; j++) {
                for (int k = 0; k < secondMatrix.length; k++) {
                    product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return product;
    }
	
	public float[][] multiply(Matrix other) {
		return multiply(other.getMatrix());
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

package com.groupe5.calculation;

import java.util.List;

import com.groupe5.geometry.Point;


/**
 * Décrit une matrice à partir d'un tableau à deux dimensions.
 * @author duhayona
 *
 */
public class Matrix {
	
	private float[][] matrix;
	
	private static final float COORDONNEE_HOMOGENE = 1.0f;

	/**
	 * Crée une matrice de coordonnées des points à partir d'une liste de points.
	 * @param points Liste des points avec lesquels on veut créer une matrice.
	 */
	public Matrix(List<Point> points) {
		
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
	
	/**
	 * Crée une matrice de coordonnées de points à partir d'un tableau à deux dimensions.
	 * @param matrix Tableau à deux dimensions de float contenant les coordonnées des points.
	 */
	public Matrix(float[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * Renvoie la matrice sous la forme d'un tableau à deux dimensions.
	 * @return Un tableau de float à deux dimensions contnenant les coordonnées des points.
	 */
	public float[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Permet de récupérer une seule colonne d'une matrice de points, ce qui correspond aux coordonnées d'un point.
	 * @param indice L'indice de la colonne dans la matrice.
	 * @return Un tableau à une dimension contenant les données de la colonne de la matrice.
	 */
	public double[] getColumn(int indice) {
		double[] coords = new double[matrix.length-1];
		for(int i = 0; i < matrix.length-1; i++) {
			coords[i] = (double) matrix[i][indice];
		}
		return coords;
		
	}
	
	/**
	 * Permet de récupérer toutes les valeurs des coordonnées X de la matrice.
	 * @return Un tableau à une dimension contenant toutes les coordonnée X de la matrice.
	 */
	public double[] getLineX() {
		double[] x = new double[matrix[0].length];		
		
		for(int i=0; i<x.length; i++) {
			x[i] = matrix[0][i];
		}
		
		return x;
	}
	
	/**
	 * Permet de récupérer toutes les valeurs des coordonnées Y de la matrice.
	 * @return Un tableau à une dimension contenant toutes les coordonnée Y de la matrice.
	 */
	public double[] getLineY() {
		double[] y = new double[matrix[0].length];
		
		for(int i=0; i<y.length; i++) {
			y[i] = matrix[1][i];
		}
		
		return y;
	}
	
	/**
	 * Permet de récupérer toutes les valeurs des coordonnées Z de la matrice.
	 * @return Un tableau à une dimension contenant toutes les coordonnée Z de la matrice.
	 */
	public double[] getLineZ() {
		double[] z = new double[matrix[0].length];
		
		for(int i=0; i<z.length; i++) {
			z[i] = matrix[2][i];
		}
		
		return z;
	}
	
	/**
	 * Calcule la multiplication entre deux matrices.
	 * @param secondMatrix La matrice sous forme de tableau à deux dimensions avec laquelle la matrice courante doit se multiplier.
	 * @return Un tableau à deux dimensions contenant la matrice avec laquelle la multiplication doit se faire.
	 */
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
	
	/**
	 * Calcule la multiplication entre deux matrices.
	 * @param other La matrice avec la quelle la matrice corante doit se multiplier.
	 * @return Un tableau à deux dimensions contenant la matrice avec laquelle la multiplication doit se faire.
	 */
	public float[][] multiply(Matrix other) {
		return multiply(other.getMatrix());
	}
	
	/**
	 * Permet de changer le tableau à deux dimensions qui décrit la matrice.
	 * @param tab le tableau à deux dimensions qui remplacera le tableau actuel.
	 */
	public void setMatrix(float[][] tab) {
		matrix = tab;
	}

	/**
	 * Permet d'obtenir une représentation sous forme de string de la matrice.
	 */
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

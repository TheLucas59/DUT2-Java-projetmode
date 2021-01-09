package com.groupe5.calculation;

/**
 * Définit l'homothétie.
 * @author duhayona
 *
 */
public class Homothety extends Matrix {
	
	/**
	 * Crée une matrice d'homothétie.
	 * @param coeff Le coefficient avec lequel doit s'appliquer l'homothétie.
	 */
	public Homothety(double coeff) {				
		super(
			new float[][] {
			 	{(float) coeff, 0, 0, 0},
			 	{0, (float) coeff, 0, 0},
			 	{0, 0, (float) coeff, 0},
			 	{0, 0, 0, 1}
			});
	}

	/**
	 * Renvoie l'homothétie sous forme d'un tableau à double dimension.
	 * @return Un tableau à double dimension de float contenant la matrice d'homothétie.
	 */
	public float[][] getHomothety() {
		return super.getMatrix();
	}	
}

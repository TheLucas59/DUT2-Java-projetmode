package com.groupe5.calculation;

/**
 * Définit la rotation autour de l'axe X.
 * @author duhayona
 *
 */
public class RotationX extends Matrix {
	/**
	 * Crée une matrice de rotation autour de l'axe X.
	 * @param teta Angle de la rotation.
	 */
	public RotationX(float teta) {
			
			super(
				new float[][] {
					{1, 0, 0, 0},
					{0, (float) Math.cos(Math.toRadians(teta)), (float) -Math.sin(Math.toRadians(teta)), 0},
					{0, (float) Math.sin(Math.toRadians(teta)), (float) Math.cos(Math.toRadians(teta)), 0},
					{0, 0, 0, 1}
				}
			);		
		}
		
	/**
	 * Renvoie la rotation sous la forme d'un tableau à deux dimensions.
	 * @return Un tableau à deux dimensions de float contenant la matrice de rotation autour de l'axe X.
	 */
	public float[][] getRotation() {
		return super.getMatrix();
	}
}

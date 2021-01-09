package com.groupe5.calculation;

/**
 * Définit la rotation autour de l'axe Z.
 * @author duhayona
 *
 */
public class RotationZ extends Matrix{
	
	/**
	 * Crée une matrice de rotation autour de l'axe Z.
	 * @param teta Angle de la rotation.
	 */
	public RotationZ(float teta) {
		
		super(
			new float[][] {
				{(float) Math.cos(Math.toRadians(teta)), (float) -Math.sin(Math.toRadians(teta)), 0, 0},
				{(float) Math.sin(Math.toRadians(teta)), (float) Math.cos(Math.toRadians(teta)), 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
			}
		);		
	}
	
	/**
	 * Renvoie la rotation sous la forme d'un tableau à deux dimensions.
	 * @return Un tableau à deux dimensions de float contenant la matrice de rotation autour de l'axe Z.
	 */
	public float[][] getRotation() {
		return super.getMatrix();
	}
	
}

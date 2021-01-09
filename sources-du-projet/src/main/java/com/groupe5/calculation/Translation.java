package com.groupe5.calculation;

import com.groupe5.geometry.Point;

/**
 * Définit la translation par rapport à un point du modèle.
 * @author duhayona
 *
 */
public class Translation extends Matrix {
	
	Point point;
	
	/**
	 * Crée une matrice de translation par rapport à un point.
	 * @param dest Point par rapport auquel se fait la translation.
	 */
	public Translation(Point dest) {				
		super(
			new float[][] {
				{1, 0, 0, dest.getX()},
			 	{0, 1, 0, dest.getY()},
			 	{0, 0, 1, dest.getZ()},
			 	{0, 0, 0, 1}
			});
		
		point = dest;
	}
	
	/**
	 * Permet d'obtenir la matrice de translation inverse de la translation courante.
	 * @return Un objet Translation qui est l'inverse de la translation courante.
	 */
	public Translation inv() {
		return new Translation(new Point(-this.getPoint().getX(),-this.getPoint().getY(), -this.getPoint().getZ(), -this.getPoint().id));
	}
	
	/**
	 * Renvoie le point vers lequel se fait la translation.
	 * @return Un objet Point qui correspond au point qui définit la translation.
	 */
	public Point getPoint() {
		return point;
	}
	
	/**
	 * Renvoie le tableau à deux dimensions qui contient la matricce de translation.
	 * @return Un tableau à deux dimensions de float qui contient la matrice de translation.
	 */
	public float[][] getTranslation() {
		return super.getMatrix();
	}
}

package com.groupe5.geometry;

/**
 * Décris un point du modèle.
 * @author plel
 *
 */
public class Point {
	private float x;
	private float y;
	private float z;
	public int id;
	
	/**
	 * Crée un point à partir de ses coordonnées.
	 * @param x Coordonnée X
	 * @param y Coordonnée Y
	 * @param z Coordoneé Z
	 * @param id
	 */
	public Point(float x, float y, float z, int id) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Getter qui renvoie la coordonnée X du point.
	 * @return Coordonnée X
	 */
	public float getX() {
		return x;
	}

	/**
	 * Getter qui renvoie la coordonnée Y du point.
	 * @return Coordonnée Y
	 */
	public float getY() {
		return y;
	}

	/**
	 * Getter qui renvoie la coordonnée Z du point.
	 * @return Coordonnée Z
	 */
	public float getZ() {
		return z;
	}
	
	/**
	 * Permet de visualiser un point dans une string.
	 * @return Renvoie une string donnant les coordonnées du point.
	 */
	public String toString() {
		return this.x + "," + this.y + "," + this.z;
	}

	/**
	 * Setter qui permet de changer la coordonnée X;
	 * @param f Nouvelle coordonnée X.
	 */
	public void setX(float f) {
		this.x = f;		
	}

	/**
	 * Setter qui permet de changer la coordonnée Y.
	 * @param f Nouvelle coordonnée Y.
	 */
	public void setY(float f) {
		this.y = f;
	}
	
	/**
	 * Setter qui permet de changer la coordonnée Z.
	 * @param f Nouvelle coordonnée Z.
	 */
	public void setZ(float f) {
		this.z = f;
	}
}

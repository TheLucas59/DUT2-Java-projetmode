package com.groupe5.geometry;

/**
 * Décris un vecteur. Est utile pour calculer l'éclairage
 * @author plel
 *
 */
public class Vector {

	private float x;
	private float y;
	private float z;
	
	/**
	 * Permet de créer un vecteur à partir de deux points.
	 * @param p1 Premier point.
	 * @param p2 Deuxième point.
	 */
	public Vector(Point p1, Point p2) {		
		x = p2.getX() - p1.getX();
		y = p2.getY() - p1.getY();
		z = p2.getZ() - p1.getZ();
	}
	
	/***
	 * Permet de créer un vecteur directement depusi des coordonnées.
	 * @param x Coordonnée X.
	 * @param y Coordonnée Y.
	 * @param z Coordonnée Z.
	 */
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Permet de renvoyer le vecteur sous la forme d'un tableau de float, contenant les coordonnées d uvecteur.
	 * @return Un tableau de float contenant les coordonnées.
	 */
	public float[] getVector() {
		return new float[] {x, y, z};
	}
	
	/**
	 * Permet de calculer le produit vectoriel entre deux vecteurs.
	 * @param other L'autre vecteur avec lequel on veut calculer le produit vectoriel.
	 * @return Le vecteur resultant du produit vectoriel.
	 */
	public Vector produitVectoriel(Vector other) {
		float newx = this.y * other.z - other.y * this.z;
		float newy = this.z * other.x - other.z * this.x;
		float newz = this.x * other.y - other.x * this.y;
		return new Vector(newx, newy, newz);
	}
	
	/**
	 * Permet de calculer le produit scalaire entre deux vecteurs.
	 * @param other L'autre vecteur avec lequel on veut calculer le produit scalaire.
	 * @return La valeur du produit scalaire.
	 */
	public float produitScalaire(Vector other) {
		float newx = this.x * other.x;
		float newy = this.y * other.y;
		float newz = this.z * other.z;
		return newx + newy + newz;
	}

	/**
	 * Permet de calculer la norme du vecteur courant.
	 * @return La norme du vecteur.
	 */
	public float norme() {
		return (float) Math.sqrt(Math.pow(this.getX(), 2.0) + Math.pow(this.getY(), 2.0) + Math.pow(this.getZ(), 2.0));
	}
	
	/**
	 * Getter pour la coordonnée X.
	 * @return Coordonnée X;
	 */
	public float getX() {
		return x;
	}

	/**
	 * Getter pour la coordonnée Y.
	 * @return Coordonnée Y.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Getter pour la coordonnée Z.
	 * @return Coordonnée Z.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Setter pour la coordonnée X.
	 * @return Coordonnée X;
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Setter pour la coordonnée Y.
	 * @return Coordonnée Y.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Setter pour la coordonnée Z.
	 * @return Coordonnée Z.
	 */
	public void setZ(float z) {
		this.z = z;
	}
	
	/**
	 * Permet de visualiser le vecteur sous forme d'une string.
	 */
	public String toString() {
		return x + " " + y + " " + z;
	}
}

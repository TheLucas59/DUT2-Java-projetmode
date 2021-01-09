package com.groupe5.geometry;

import java.util.List;

/**
 * Décris une face d'un modèle. Implémente l'interface Comparable pour pouvoir les trier.
 * @author plel
 *
 */
public class Face implements Comparable<Face> {
	private List<Integer> indices;
	
	/**
	 * Crée un objet face avec une liste contenant les indices des points dans la matrice.
	 * @param indices Liste des indices.
	 */
	public Face(List<Integer> indices) {
		this.indices = indices;
	}
	
	/**
	 * Renvoie la taille de la liste (attention, pour avoir le nombre de points de la face il faut retirer 1 ou récupérer le premier objet de la liste).
	 * @return La taille de la liste.
	 */
	public int getNbPoints() {
		return indices.size();
	}

	/**
	 * Renvoie la liste des indices des points.
	 * @return Liste des indices en Integer.
	 */
	public List<Integer> getPoints() {
		return indices;
	}

	/**
	 * Obligatoire pour l'implémentation de l'interface Comparable, n'est utilisée nulle part : voir FaceComparator.
	 */
	@Override
	public int compareTo(Face o) {
		return 1;
	}

	/**
	 * Permet de visualiser la liste dans une String.
	 */
	@Override
	public String toString() {
		return indices.toString();
	}
}

package com.groupe5.geometry;

import java.util.Comparator;

/**
 * Permet la comparaison des faces d'un objet Modele3D.
 * @author plel
 *
 */

public class FaceComparator<T> implements Comparator<Face> {
	private Modele3D modele;
	
	/**
	 * Crée un objet FaceComparator à partir d'un modèle.
	 * @param modele Modèle à partir duquel on crée le comparateur.
	 */
	public FaceComparator(Modele3D modele) {
		this.modele = modele;
	}
	
	/**
	 * Permet de comparer de face entre elles. La comparaison se fait via la moyenne des coordonnées Z des faces.
	 * @param o1 La face à comparer à o2.
	 * @param o2 Une face qu'on compare à o1.
	 * @return -1 si la face o1 a une moyenne des Z supérieure à o2, 1 si au contraire o2 a une moyenne supérieure à o1, 0 si les moyennes sont égales.
	 */
	@Override
	public int compare(Face o1, Face o2) {
		double moyF1;
		double moyF2;
		double total = 0;

		for(int i = 1; i < o1.getNbPoints(); i++) {
			int indicePoint = o1.getPoints().get(i);
			double z = modele.getPoints().getColumn(indicePoint)[2];
			total += z;
		}
		moyF1 = total/o1.getNbPoints();
		total = 0;
		
		for(int i = 1; i < o2.getNbPoints(); i++) {
			int indicePoint = o2.getPoints().get(i);
			double z = modele.getPoints().getColumn(indicePoint)[2];
			total += z;
		}
		moyF2 = total/o2.getNbPoints();
		if(moyF1 > moyF2) {
			return -1;
		}
		if(moyF2 > moyF1) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
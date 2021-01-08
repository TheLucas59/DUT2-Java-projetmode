package com.groupe5.geometry;

import java.util.Comparator;

public class FaceComparator<T> implements Comparator<Face> {
	private Modele3D modele;
	
	public FaceComparator(Modele3D modele) {
		this.modele = modele;
	}

	@Override
		public int compare(Face o1, Face o2) {
			double moyF1;
			double moyF2;
			double total = 0;

			for(int i = 0; i < o1.getNbPoints(); i++) {
				int indicePoint = o1.getPoints().get(i);
				double z = modele.getPoints().getColumn(indicePoint)[2];
				total += z;
			}
			moyF1 = total/o1.getNbPoints();
			total = 0;
			
			for(int i = 0; i < o2.getNbPoints(); i++) {
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
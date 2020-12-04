package com.groupe5.geometry;

import java.util.Collections;
import java.util.List;

import com.groupe5.calculation.Matrix;

public class Modele3D {
	private Matrix points;
	private List<Face> faces;
	
	public Modele3D(Matrix points, List<Face> faces) {
		this.points = points;
		this.faces = faces;
	}

	public Matrix getPoints() {
		return points;
	}
	
	public List<Face> getFaces() {
		return this.faces;
	}
	
	public void setMatrix(Matrix other) {
		this.points = other;
	}
	
	public void sortFaces() {
		Collections.sort(faces, new FaceComparator<Face>(this));
	}

}

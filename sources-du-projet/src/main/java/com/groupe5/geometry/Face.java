package com.groupe5.geometry;

import java.util.List;

public class Face implements Comparable<Face> {
	private List<Integer> indices;
	
	public Face(List<Integer> indices) {
		this.indices = indices;
	}
	
	public int getNbPoints() {
		return indices.size();
	}

	public List<Integer> getPoints() {
		return indices;
	}

	@Override
	public int compareTo(Face o) {
		return 1;
	}
	
}

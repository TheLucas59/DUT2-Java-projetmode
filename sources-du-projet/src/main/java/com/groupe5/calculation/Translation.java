package com.groupe5.calculation;

import com.groupe5.geometry.Vector;

public class Translation extends Matrix {

	public Translation(Vector vector) {
		
		super(new float[] {1, 0, 0, vector.getEndPoint()[0]},
				new float[] {0, 1, 0, vector.getEndPoint()[1]},
				new float[] {0, 0, 1, vector.getEndPoint()[2]},
				new float[] {0, 0, 0, 1});
		
	}
	
	public float[][] getTranslation() {
		return super.getMatrix();
	}
}

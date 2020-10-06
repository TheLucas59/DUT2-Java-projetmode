package com.groupe5.calculation;

import com.groupe5.geometry.Vector;

public class Translation extends Matrix {

	public Translation(Vector vector) {
		
		super(new double[] {1, 0, 0, vector.getEndPoint()[0]},
				new double[] {0, 1, 0, vector.getEndPoint()[1]},
				new double[] {0, 0, 1, vector.getEndPoint()[2]},
				new double[] {0, 0, 0, 1});
		
	}
	
	public double[][] getTranslation() {
		return super.getMatrix();
	}
}

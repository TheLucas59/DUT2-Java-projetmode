package com.groupe5.calculation;

import com.groupe5.geometry.Point;

public class Translation extends Matrix {
	
	Point point;
	
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
	
	public Translation inv() {
		return new Translation(new Point(-this.getPoint().getX(),-this.getPoint().getY(), -this.getPoint().getZ(), -this.getPoint().id));
	}
	
	public Point getPoint() {
		return point;
	}
	
	public float[][] getTranslation() {
		return super.getMatrix();
	}
}

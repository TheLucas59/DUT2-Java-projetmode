package com.groupe5.calculation;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.groupe5.geometry.Point;

class TestTranslation {
	
	ArrayList<Point> points;
	Translation t1;
	Translation t2;
	Point p1;
	Point p2;
	Point p3;
	Point p4;
	Point dest1;
	Point dest2;
	Matrix m;
	
	@BeforeEach
	void setup() {
		points = new ArrayList<>();
		p1 = new Point(1, 1, 1, 0);
		p2 = new Point(2, 2, 2, 0);
		p3 = new Point(3, 3, 3, 0);
		p4 = new Point(4, 4, 4, 0);
		points.add(p1); points.add(p2); points.add(p3); points.add(p4);
		m = new Matrix(points);
		dest1 = new Point(5, 5, 5, 0);
		dest2 = new Point(6, 6, 6, 0);
		t1 = new Translation(dest1);
		t2 = new Translation(dest2);
	}

	@Test
	void testMultiplyWithATranslation() {
		float[][] expectedResult = {
				{6, 7, 8, 9},
				{6, 7, 8, 9},
				{6, 7, 8, 9},
				{1, 1, 1, 1}
		};
		assertArrayEquals(expectedResult, t1.multiply(m));
		
		float[][] expectedResult2 = {
				{7, 8, 9, 10},
				{7, 8, 9, 10},
				{7, 8, 9, 10},
				{1, 1, 1, 1}
		};
		assertArrayEquals(expectedResult2, t2.multiply(m));
	}
	
	@Test
	void testMultiplyWithANullTranslation() {
		Translation nullTranslation = new Translation(new Point(0, 0, 0, 0));
		
		float[][] expectedResult1 = {
				{1, 2, 3, 4},
				{1, 2, 3, 4},
				{1, 2, 3, 4},
				{1, 1, 1, 1}
		};
		
		assertArrayEquals(expectedResult1, nullTranslation.multiply(m));
	}

}

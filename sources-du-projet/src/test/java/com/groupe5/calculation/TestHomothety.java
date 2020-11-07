package com.groupe5.calculation;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.groupe5.geometry.Point;

class TestHomothety {

	ArrayList<Point> points;
	Homothety h1;
	Homothety h2;
	Point p1;
	Point p2;
	Point p3;
	Point p4;
	Matrix m;
	
	@BeforeEach
	void setup() {
		points = new ArrayList<Point>();
		p1 = new Point(1, 1, 1, 0);
		p2 = new Point(2, 2, 2, 0);
		p3 = new Point(3, 3, 3, 0);
		p4 = new Point(4, 4, 4, 0);
		points.add(p1); points.add(p2); points.add(p3); points.add(p4);
		m = new Matrix(points);
		h1 = new Homothety(2.0);
		h2 = new Homothety(0);
	}
	
	@Test
	void testMultiplicationWithAHomothety() {
		float[][] expectedResult = {
				{2, 4, 6, 8},
				{2, 4, 6, 8},
				{2, 4, 6, 8},
				{1, 1, 1, 1}
		};
		assertArrayEquals(expectedResult, h1.multiply(m));
	}

	void testMultiplicationWithA0CoeffHomothety() {
		float[][] expectedResult = {
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 1, 1, 1}
		};
		assertArrayEquals(expectedResult, h2.multiply(m));
	}
}

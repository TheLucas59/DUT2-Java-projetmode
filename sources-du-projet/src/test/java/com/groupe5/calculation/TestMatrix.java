package com.groupe5.calculation;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.groupe5.geometry.Point;

class TestMatrix {

	List<Point> points1;
	List<Point> points2;
	Point p1;
	Point p2;
	Point p3;
	Point p4;
	Point p5;
	Point p6;
	Point p7;
	Point p8;
	Matrix m1;
	Matrix m2;
	
	@BeforeEach
	void setup() {
		points1 = new ArrayList<>();
		points2 = new ArrayList<>();
		p1 = new Point(1, 1, 1, 0);
		p2 = new Point(2, 2, 2, 0);
		p3 = new Point(3, 3, 3, 0);
		p4 = new Point(4, 4, 4, 0);
		p5 = new Point(5, 5, 5, 0);
		p6 = new Point(6, 6, 6, 0);
		p7 = new Point(7, 7, 7, 0);
		p8 = new Point(8, 8, 8, 0);
		points1.add(p1); points1.add(p2); points1.add(p3); points1.add(p4); 
		points2.add(p5); points2.add(p6); points2.add(p7); points2.add(p8);
		m1 = new Matrix(points1);
		m2 = new Matrix(points2);
	}
	
	@Test
	void testMultiplyMatrix() {
		float[][] expectedResult = {
				{34, 40, 46, 52},
				{34, 40, 46, 52},
				{34, 40, 46, 52},
				{16, 19, 22, 25}
		};
		assertArrayEquals(expectedResult, m1.multiply(m2));
	}
	
	@Test
	void testMultiplyWithANullMatrix() {
		float[][] nullTab = {
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 1, 1, 1}
		};
		
		Matrix nullMatrix = new Matrix(nullTab);
		
		float[][] expectedResultWithM1 = {
			{0, 0, 0, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0},
			{4, 7, 10, 13}
		};
		
		assertArrayEquals(expectedResultWithM1, nullMatrix.multiply(m1));
		
		float[][] expectedResultWithM2 = {
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{16, 19, 22, 25}
			};
		
		assertArrayEquals(expectedResultWithM2, nullMatrix.multiply(m2));
	}

}

package com.groupe5.geometry;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestVector {

	Vector v1;
	Vector v2;
	Vector v3;
	Point p1;
	Point p2; 
	
	@BeforeEach
	void setup() {
		v1 = new Vector(2,3,5);
		p1 = new Point(1,2,1,0);
		p2 = new Point(5,4,3,0);
		v2 = new Vector(p1, p2);
		v3 = new Vector(0,0,0);
	}
	
	
	@Test
	void testProduitVectoriel() {
		assertArrayEquals(new float[] {-4, 16, -8}, v1.produitVectoriel(v2).getVector());
		assertArrayEquals(new float[] {4, -16, 8}, v2.produitVectoriel(v1).getVector());
		assertArrayEquals(new float[] {0, 0, 0}, v1.produitVectoriel(v3).getVector());
	}
	
	@Test
	void testProduitScalaire() {
		assertEquals((float) 24, v1.produitScalaire(v2));
		assertEquals((float) 24, v2.produitScalaire(v1));
		assertEquals(0, v1.produitScalaire(v3));
	}
	
	@Test
	void testNorme() {
		assertEquals(Math.sqrt(38), v1.norme(), 0.01);
		assertEquals(2*Math.sqrt(6), v2.norme(), 0.01);
		assertEquals(0, v3.norme());
	}

}

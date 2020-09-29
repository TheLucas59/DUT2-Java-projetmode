package com.groupe5.parser;

import java.io.File;
import java.util.ArrayList;

import com.groupe5.geometry.Face;
import com.groupe5.geometry.Point;
import com.groupe5.geometry.Vector;

public class PLYFile {
	File file;
	String header;
	ArrayList<Face> faces;
	ArrayList<Point> points;
	ArrayList<Vector> vectors;
	
	PLYFile(File file){
		this.file = file;
		faces = new ArrayList<Face>();
		points = new ArrayList<Point>();
		vectors = new ArrayList<Vector>();
	}
	
	public boolean parse() {
		Parser p = new Parser(this.file);
		p.parse();
		header = p.getHeader();
		faces = p.getFaces();
		points = p.getPoints();
		vectors = p.getVectors();
		return true;
	}
}

package com.groupe5.parser;

import java.io.File;
import java.util.ArrayList;

import com.groupe5.geometry.Face;
import com.groupe5.geometry.Point;
import com.groupe5.geometry.Vector;

public class Parser {
	File file;
	String header;
	String faces;
	String points;
	String vectors;
	
	Parser(File file){
		this.file = file;
	}
	
	// séparer toute les catégories du fichier
	public void parse() {
		
	}

	// renvoyer les objets apropriés
	public String getHeader() {
		return header;
	}

	public ArrayList<Face> getFaces() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Point> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Vector> getVectors() {
		// TODO Auto-generated method stub
		return null;
	}
}

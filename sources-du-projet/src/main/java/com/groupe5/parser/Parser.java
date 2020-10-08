package com.groupe5.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.groupe5.geometry.Face;
import com.groupe5.geometry.Point;

public class Parser {
	String header;
	String faces;
	String points;
	
	public Parser(File file) throws IOException{
		parse(file);
	}
	
	// séparer toute les catégories du fichier
	public void parse(File file) throws IOException {
		String everything = readFile(file);
		Scanner s = new Scanner(everything);
		StringBuilder currentStack = new StringBuilder();
		
		// parse header
		while(s.hasNext()){
			String line = s.nextLine();
			currentStack.append(line).append("\n");
			if(line.startsWith("end_header")) {
				// we are at the end of the header; set it and break
				header = currentStack.toString();
				break;
			}
		}
		
		// temp variables; will be parsed from header later on, for now using defaults
		int faceLineLength = 4;
		
		// parse points
		while(s.hasNext()){
			String line = s.nextLine();
			currentStack.append(line).append("\n");
			String[] split = line.split(" ");
			if(split.length == faceLineLength) {
				// we encountered a face; set it and break
				points = currentStack.toString();
				break;
			}
		}
		
		// parse faces
		
		while(s.hasNext()){
			String line = s.nextLine();
			currentStack.append(line).append("\n");
		}
		faces = currentStack.toString();
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
	
	public String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line).append("\n");
		        line = br.readLine();
		    }
		    return(sb.toString());
		} finally {
		    br.close();
		}
	}
}

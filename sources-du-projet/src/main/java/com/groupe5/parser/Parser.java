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
		String line;
		// parse header
		while(s.hasNext()){
			line = s.nextLine();
			currentStack.append(line).append("\n");
			if(line.startsWith("end_header")) {
				// we are at the end of the header; set it and break
				header = currentStack.toString();
				break;
			}
		}
		currentStack = new StringBuilder();
		// temp variables; will be parsed from header later on, for now using defaults
		int faceLineLength = 4;
		
		// parse points
		while(s.hasNext()){
			line = s.nextLine();
			String[] split = line.split(" ");
			if(split.length == faceLineLength) {
				// we encountered a face; set it and break
				points = currentStack.toString();
				currentStack = new StringBuilder();
				currentStack.append(line).append("\n");
				break;
			}
			currentStack.append(line).append("\n");
		}
		
		// parse faces
		while(s.hasNext()){
			currentStack.append(s.nextLine()).append("\n");
		}
		faces = currentStack.toString();
		s.close();
	}

	// renvoyer les objets apropriés
	
	public String getHeader() {
		return header;
	}
	
	public ArrayList<Point> getPoints() {
		if(this.points == null) {
			return null;
		}
		ArrayList<Point> r = new ArrayList<Point>();
		Scanner s = new Scanner(this.points);
		int currentLine = -1;
		while(s.hasNext()){
			currentLine++;
			String line = s.nextLine();
			String[] split = line.split(" ");
			if(split.length == 3) {
				r.add(new Point(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]), currentLine));
			}			
		}
		s.close();
		return r;
	}

	public ArrayList<Face> getFaces(ArrayList<Point> points) {
		if(points == null || this.faces == null) {
			return null;
		}
		ArrayList<Face> f = new ArrayList<Face>();
		Scanner s = new Scanner(this.faces);
		while(s.hasNext()){
			String line = s.nextLine();
			String[] split = line.split(" ");
			if(split.length == 4) {
				int p1idx = Integer.parseInt(split[1]);
				int p2idx = Integer.parseInt(split[2]);
				int p3idx = Integer.parseInt(split[3]);
				Point[] p = new Point[]{points.get(p1idx), points.get(p2idx), points.get(p3idx)};
				f.add(new Face(Integer.parseInt(split[0]), p));
			}
		}
		s.close();
		return f;
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

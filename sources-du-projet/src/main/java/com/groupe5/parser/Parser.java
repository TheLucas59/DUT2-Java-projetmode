package com.groupe5.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.groupe5.geometry.Face;
import com.groupe5.geometry.Point;

public class Parser {
	String header;
	String faces;
	String points;

	List<Integer> idPoints;
	
	public Parser(File file) throws IOException{
		parse(file);
	}
	
	// séparer toute les catégories du fichier
	public void parse(File file) throws IOException {
		String everything = readFile(file);
		Scanner s = new Scanner(everything);
		StringBuilder currentStack = new StringBuilder();
		String line;
		Integer vertexCount = null;
		Integer faceCount = null;
		// parse header
		while(s.hasNext()){
			line = s.nextLine();
			currentStack.append(line).append("\n");
			if(line.startsWith("element vertex")) {
				vertexCount = Integer.parseInt(line.split(" ")[2]);				
			}
			if(line.startsWith("element face")) {
				faceCount = Integer.parseInt(line.split(" ")[2]);
			}
			if(line.startsWith("end_header")) {
				// we are at the end of the header; set it and break
				header = currentStack.toString();
				break;
			}
		}
		currentStack = new StringBuilder();
		
		if(vertexCount == null || faceCount == null) {
			System.out.println("ERROR PARSING THE MODEL : Incorrect header");
			s.close();
			return;
		}
		
		// parse points
		for(int i = 0; i < vertexCount; i++){
			currentStack.append(s.nextLine()).append("\n");
		}
		points = currentStack.toString();
		currentStack = new StringBuilder();
		
		// parse faces
		for(int i = 0; i < faceCount; i++){
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

		idPoints = new ArrayList<>();

		Scanner s = new Scanner(this.faces);
		while(s.hasNext()){
			String line = s.nextLine();
			String[] split = line.split(" ");
			int faceSize = Integer.parseInt(split[0]);
			Point[] p = new Point[faceSize+1];
			System.out.print(faceSize + " ");
			for(int i = 0; i <= faceSize; i++) {
				int idx = Integer.parseInt(split[i]);
				idPoints.add(idx);
				p[i] = points.get(idx);
			}
			f.add(new Face(faceSize, p));
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

package com.groupe5.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.groupe5.geometry.Face;
import com.groupe5.geometry.Point;

public class PLYFile {
	public File file;
	public String header;
	public ArrayList<Face> faces;
	public ArrayList<Point> points;
	int faceCount;
	int vertexCount;
	String fileFormat;

	public PLYFile(File file){
		this.file = file;
		header = "";
		faces = new ArrayList<Face>();
		points = new ArrayList<Point>();
	}
	
	public File getFile() {
		return this.file;
	}
	
	public String getFormat(){
		if(this.header == "") {
			this.onlyHeader();
		}
		return fileFormat;
	}
	
	public int getTotalPoints(){
		if(this.header == "") {
			this.onlyHeader();
		}
		return vertexCount;
	}
	
	public int getTotalFaces(){
		if(this.header == "") {
			this.onlyHeader();
		}
		return faceCount;
	}
	
	// returns false if parsing fails
	public boolean parse() {
		Parser p;
		try {
			p = new Parser(this.file);
		} catch (IOException e) {
			return false;
		}
		header = p.getHeader();
		points = p.getPoints();
		faces = p.getFaces(points);
		getFields();
		return true;
	}
	
	public boolean onlyHeader() {
		Parser p;
		try {
			p = new Parser(this.file);
		} catch (IOException e) {
			return false;
		}
		header = p.getHeader();
		getFields();
		return true;
	}
	
	public String toString() {
		return this.file.getName();
	}
	
	public void getFields() {
		String[] fields = header.split("\n");
		for(int i = 0; i < fields.length; i++) {
			if(i == 0) {
				this.fileFormat = fields[i];
			}
			if(fields[i].startsWith("element vertex")) {
				vertexCount = Integer.parseInt(fields[i].split(" ")[2]);				
			}
			if(fields[i].startsWith("element face")) {
				faceCount = Integer.parseInt(fields[i].split(" ")[2]);
			}
		}
	}
}

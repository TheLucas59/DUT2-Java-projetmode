package com.groupe5.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.groupe5.geometry.Face;
import com.groupe5.geometry.Point;
import com.groupe5.view.FileChooser;

public class PLYFile {
	public File file;
	public String header;
	public ArrayList<Face> faces;
	public ArrayList<Point> points;
	int faceCount;
	int vertexCount;
	String fileFormat;
	String comment = "(aucun)";

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
		if(fileFormat == null) {
			this.onlyHeader();
		}
		return fileFormat;
	}
	
	public int getTotalPoints(){
		if(vertexCount == 0) {
			this.onlyHeader();
		}
		return vertexCount;
	}
	
	public int getTotalFaces(){
		if(faceCount == 0) {
			this.onlyHeader();
		}
		return faceCount;
	}
	
	public String getComment() {
		if(comment == null) {
			this.onlyHeader();
		}
		return comment;
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
		try {
			faces = p.getFaces(points);
		} catch (Exception e) {
			FileChooser.showAlert("Exception", "Error Message", e.getMessage());
		}
		getFields();
		return true;
	}
	
	public boolean onlyHeader() {
		if(header.length() != 0) return false;
		Parser p;
		try {
			p = new Parser(this.file);
		} catch (Exception e) {
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
			if(fields[i].startsWith("comment")) {
				if(comment.equals("(aucun)")) {
					comment = fields[i].replaceFirst("comment ", "");
				} else {
					comment += "\n" + fields[i].replaceFirst("comment ", "");
				}
			}
		}
	}
}

package com.groupe5.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.groupe5.geometry.Face;
import com.groupe5.geometry.Point;
import com.groupe5.view.FileChooser;
/**
 * Descripteur de fichier PLY
 * @author demayl
 *
 */
public class PLYFile {
	public File file;
	public String header;
	public ArrayList<Face> faces;
	public ArrayList<Point> points;
	int faceCount;
	int vertexCount;
	String fileFormat;
	String comment = "(aucun)";

	/**
	 * Crée un objet de PLYFile tout en initialisant ses ArrayLists.
	 * @param file Fichier PLY
	 */
	public PLYFile(File file){
		this.file = file;
		header = "";
		faces = new ArrayList<>();
		points = new ArrayList<>();
	}
	
	/**
	 * Renvoie le fichier
	 * @return File file
	 */
	public File getFile() {
		return this.file;
	}
	
	/**
	 * Parse le fichier si besoin, sinon renvoie le format de fichier
	 * @return String fileFormat
	 */
	public String getFormat(){
		if(fileFormat == null) {
			this.onlyHeader();
		}
		return fileFormat;
	}
	
	/**
	 * Parse le fichier si besoin, sinon renvoie le nombres de points
	 * @return int vertexCount
	 */
	public int getTotalPoints(){
		if(vertexCount == 0) {
			this.onlyHeader();
		}
		return vertexCount;
	}
	
	/**
	 * Parse le fichier si besoin, sinon renvoie le nombres de faces
	 * @return int faceCount
	 */
	public int getTotalFaces(){
		if(faceCount == 0) {
			this.onlyHeader();
		}
		return faceCount;
	}
	
	/**
	 * Parse le fichier si besoin, sinon renvoie le commentaire
	 * @return commentaire du fichier ou "(aucun)" si aucun commentaire n'est spécifié
	 */
	public String getComment() {
		if(comment == null) {
			this.onlyHeader();
		}
		return comment;
	}
	
	/**
	 * Fait les appels aux fonctions de Parser pour affecter les ArrayLists de header, points et faces
	 * @return false si le fichier n'a pas pu être parsé
	 */
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
		} catch (NumberFormatException e) {
			FileChooser.showAlert("Exception", "Error Message", e.getMessage());
		}
		catch (NoSuchElementException e) {
			FileChooser.showAlert("Exception", "Error Message", e.getMessage());
		}
		getFields();
		return true;
	}
	
	/**
	 * Méthode permettant de parser uniquement le header pour améliorer le temps de chargement du navigateur de fichier
	 * @return false si le fichier n'a pas pu être parsé
	 */
	public boolean onlyHeader() {
		if(header.length() != 0) return false;
		Parser p;
		try {
			p = new Parser(this.file);
		} catch (NumberFormatException | NoSuchElementException | IOException e) {
			return false;
		}
		header = p.getHeader();
		getFields();
		return true;
	}
	
	/**
	 * Initialise les champs a partir du header pour le navigateur 
	 */
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

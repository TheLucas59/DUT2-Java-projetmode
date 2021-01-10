package com.groupe5.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.groupe5.calculation.Matrix;
import com.groupe5.calculation.Translation;
import com.groupe5.geometry.Face;
import com.groupe5.geometry.Modele3D;
import com.groupe5.geometry.Point;
import com.groupe5.observerpattern.Observer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Class abstraite qui contient certaines méthodes utiles pour l'affichage
 * @author pirca
 * @author plel
 * @author duhayona
 * @author demayl
 */

public abstract class Viewer implements Observer {

	public static File fileShow;
	public abstract Translation getCenter();
	public abstract Text getZoomText();
	public abstract double getOldZoom();
	public abstract Slider getSlideZoom();
	public abstract void clearScreen();

	/**
	 * Créer un nouveau modèle 3D
	 * @param points Matrice des points de l'objet.
	 * @param faces Liste des faces de l'objet.
	 * @param view L'instance de la vue où sera affiché l'objet.
	 * @param fileShow Le fichier où sont écrites les informations à afficher.
	 * @return Un nouveau Modele3D
	 */
	public Modele3D newModele(Matrix points, ArrayList<Face> faces, Viewer view, File fileShow) {
		return new Modele3D(points, faces, view, fileShow);
	}
	
	
	/**
	 * Calcule le point central de l'objet affiché
	 * @param points Liste des points de l'objet.
	 * @return Un point situé au centre de l'objet.
	 */
	public Point setObjectCenter(ArrayList<Point> points) {
		double x = 0, y = 0, z = 0;
		int size = points.size();

		for(Point p : points) {
			x += p.getX();
			y += p.getY();
			z += p.getZ();
		}

		return new Point(((float) x/size), ((float) y/size), ((float) z/size), 0);
	}

	/**
	 * Renvoie le fichier dans lequel se trouve les informations de l'objet affiché
	 * @return Un objet Java de type File, contenant les informations du fichier utilisé.
	 */
	public static File getFileToShow() {
		return fileShow;
	}

	/**
	 * Méthode qui dessine le modèle dans les différentes vues
	 * @param modele Le modèle à afficher
	 * @param gc La zone dans laquelle il faut dessiner
	 * @param showLines Un boolean qui permet de savoir s'il faut afficher les lignes des faces
	 * @param showFaces Un boolean qui permet de savoir s'il faut mettre en couleur les faces de l'objet
	 */
	public void drawObject(Modele3D modele, GraphicsContext gc, boolean showLines, boolean showFaces) {	
		if(modele != null) {

			clearScreen();

			modele.sortFaces();
			gc.setStroke(Color.GRAY);
			gc.setLineWidth(0.5);

			List<Face> faces = modele.getFaces();

			for(Face f : faces) {
				double[] pointsX = getCoordsX(modele, f);
				double[] pointsY = getCoordsY(modele, f);
				if(showFaces) {
					float eclairage = modele.eclairageFace(f);

					if(eclairage != -1) {
						gc.setFill(Color.rgb(Math.round(255*eclairage), Math.round(255*eclairage), Math.round(255*eclairage)));
					}
					else {
						gc.setFill(Color.WHITE);
					}
					gc.fillPolygon(pointsX, pointsY, f.getNbPoints()-1);
				}
				if(showLines)
					gc.strokePolygon(pointsX, pointsY, f.getNbPoints()-1);
			}
		}
	}

	/**
	 * Méthode permettant de récupérer toutes les coordonnées situées sur l'axe X de l'objet
	 * @param modele Le modèle dont on veut les coordonnées
	 * @param f La face dont on veut les coordonnées
	 * @return Un tableau contenant les coordonnées.
	 */
	private double[] getCoordsX(Modele3D modele, Face f) {
		double[] result = new double[f.getNbPoints()-1];
		int j = 0;
		for(Integer i : f.getPoints()) {
			if(j != 0) {
				result[j-1] = modele.getPoints().getColumn(i)[0];
			}
			j++;
		}
		return result;
	}

	/**
	 * Méthode permettant de récupérer toutes les coordonnées situées sur l'axe Y de l'objet
	 * @param modele Le modèle dont on veut les coordonnées
	 * @param f La face dont on veut les coordonnées
	 * @return Un tableau contenant les coordonnées.
	 */
	private double[] getCoordsY(Modele3D modele, Face f) {
		double[] result = new double[f.getNbPoints()];
		int j = 0;
		for(Integer i : f.getPoints()) {
			if(j != 0) {
				result[j-1] = modele.getPoints().getColumn(i)[1];
			}
			j++;
		}
		return result;
	}
}

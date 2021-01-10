package com.groupe5.geometry;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.groupe5.calculation.Homothety;
import com.groupe5.calculation.Matrix;
import com.groupe5.calculation.RotationX;
import com.groupe5.calculation.RotationY;
import com.groupe5.calculation.Translation;
import com.groupe5.observerpattern.Observed;
import com.groupe5.view.Viewer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Décris un modèle chargé à partir d'un fichier
 * @author plel, duhayona
 *
 */

public class Modele3D extends Observed {
	private Matrix points;
	private List<Face> faces;
	private Viewer view;
	private File fileShow;
	public final Vector lumiere = new Vector(1,1,0);

	private Timeline rotation;

	/**
	 * Crée un objet Modele3D à partir d'une matrice de points et d'une liste de faces. Met en place également une TimeLine pour la rotation automatique.
	 * @param points Matrice des points du modèle
	 * @param faces Liste des indices des points du modèle
	 * @param view Vue qui utilise ce modèle
	 * @param fileToShow Le fichier qui décrit ce modèle
	 */
	public Modele3D(Matrix points, List<Face> faces, Viewer view, File fileToShow) {
		this.points = points;
		this.faces = faces;
		this.view = view;
		this.fileShow = fileToShow;

		RotationY ry = new RotationY(1);

		KeyFrame begin = new KeyFrame(Duration.seconds(0));
		KeyFrame end = new KeyFrame(Duration.millis(16.6), rot -> {
			this.getPoints().setMatrix(view.getCenter().multiply(ry.multiply((view.getCenter()).inv().multiply(this.getPoints()))));
			zoom();
		});

		rotation = new Timeline(begin, end);
		rotation.setCycleCount(Timeline.INDEFINITE);

		attach(view);
	}
	
	/**
	 * Permet d'attacher une nouvelle vue au modèle.
	 * @param view Vue à attacher
	 */
	public void newView(Viewer view) {
		attach(view);
	}

	/**
	 * Getter permettant de récupérer la matrice des points.
	 * @return Un objet Matrix contenant les coordonnées des points.
	 */
	public Matrix getPoints() {
		return points;
	}

	/**
	 * Getter permettant de récupérer la liste des faces.
	 * @return Une liste contenant les faces du modèle.
	 */
	public List<Face> getFaces() {
		return this.faces;
	}

	/**
	 * Setter permettant de changer la matrice des points. 
	 * @param other Matrice qui va remplacer les points existants.
	 */
	public void setMatrix(Matrix other) {
		this.points = other;
	}

	/**
	 * Permet de trier les faces par rapport à leur coordonnée Z (voir la classe FaceComparator pour plus d'informations).
	 */
	public void sortFaces() {
		Collections.sort(faces, new FaceComparator<Face>(this));
	}

	/**
	 * Calcule le zoom du modèle lorsqu'on scroll avec la souris ou que la valeur est changée avec le slider.
	 * 
	 */
	public void zoom() {
		view.getZoomText().setText("ZOOM : " + Math.round(view.getSlideZoom().getValue()) + "%");

		this.getPoints().setMatrix(view.getCenter().inv().multiply(this.getPoints()));
		Homothety h1 = new Homothety(1/view.getOldZoom());		
		this.getPoints().setMatrix(h1.multiply(this.getPoints()));

		Homothety h2 = new Homothety(view.getSlideZoom().getValue());		

		Matrix removeCenter = new Matrix(view.getCenter().multiply(h2.multiply(this.getPoints())));
		this.setMatrix(removeCenter);
		notifyObservers();
	}

	/**
	 * Calcule la rotation du modèle lorsqu'on utilise le clic gauche de la souris
	 * @param e MouseEvent généré par JavaFX lors du clic de la souris
	 * @param oldMousePosX ancienne coordonnée X de la souris lors du précédent clic
	 * @param oldMousePosY ancienne coordonnée Y de la souris lors du précédent clic
	 */
	public void rotate(MouseEvent e, double oldMousePosX, double oldMousePosY) {
		double mousePosX = e.getSceneX();
		double mousePosY = e.getSceneY();

		RotationX rx = new RotationX((float) ((mousePosY - oldMousePosY)));
		RotationY ry = new RotationY((float) ((mousePosX - oldMousePosX)));

		Matrix completeRotation = new Matrix(rx.multiply(ry));
		this.getPoints().setMatrix(view.getCenter().multiply(completeRotation.multiply(view.getCenter().inv().multiply(this.getPoints()))));

		zoom();
	}

	/**
	 * Calcule la translation du modèle lorsqu'on utilise le clic droit de la souris
	 * @param e MouseEvent généré par JavaFX lors du clic de la souris
	 * @param oldMousePosX ancienne coordonnée X de la souris lors du précédent clic
	 * @param oldMousePosY ancienne coordonnée Y de la souris lors du précédent clic
	 */
	public void translation(MouseEvent e, double oldMousePosX, double oldMousePosY) {
		double mousePosX = e.getSceneX();
		double mousePosY = e.getSceneY();

		Point pointTranslate = new Point((float) ((mousePosX - oldMousePosX)), (float) ((mousePosY - oldMousePosY)), (float) 0.0, 0); 
		Translation t = new Translation(pointTranslate);

		this.getPoints().setMatrix(view.getCenter().multiply(t.multiply(view.getCenter().inv().multiply(this.getPoints()))));

		zoom();
	}

	/**
	 * Retourne le coefficient permettant l'assombrissement des faces.
	 * @param f face pour laquelle il faut calculer le coefficient
	 * @return le coefficient d'éclairage de la face f si le produit scalaire est supérieur à 0, sinon -1
	 */
	public float eclairageFace(Face f) {
		List<Integer> pointsFace = f.getPoints();
		float[][] pointsModele = this.points.getMatrix();
		Point p1 = new Point(pointsModele[0][pointsFace.get(1)], pointsModele[1][pointsFace.get(1)], pointsModele[2][pointsFace.get(1)], 0);
		Point p2 = new Point(pointsModele[0][pointsFace.get(2)], pointsModele[1][pointsFace.get(2)], pointsModele[2][pointsFace.get(2)], 0);
		Point p3 = new Point(pointsModele[0][pointsFace.get(3)], pointsModele[1][pointsFace.get(3)], pointsModele[2][pointsFace.get(3)], 0);
		Vector p1p2 = new Vector(p1, p2);
		Vector p1p3 = new Vector(p1, p3);
		Vector normal = p1p2.produitVectoriel(p1p3);
		float normeNormal = normal.norme();
		float normeLumiere = lumiere.norme();
		normal.setX(normal.getX()/normeNormal);
		normal.setY(normal.getY()/normeNormal);
		normal.setZ(normal.getZ()/normeNormal);
		lumiere.setX(lumiere.getX()/normeLumiere);
		lumiere.setY(lumiere.getY()/normeLumiere);
		lumiere.setZ(lumiere.getZ()/normeLumiere);
		float scalaire = normal.produitScalaire(lumiere);
		if(scalaire > 0) {
			float teta = (float) Math.cos(scalaire);
			return teta;
		}
		return -1;
	}

	/**
	 * Permet la rotation automatique du modèle.
	 * @param modele Modele où doit s'appliquer la rotation.
	 * @param action Permet de déterminer si la rotation doit commencer où s'arrêter.
	 */
	public void rotationAuto(Modele3D modele, String action) {	
		switch(action) {
		case "run":
			rotation.play();
			break;
		case "stop":
			rotation.stop();
			break;
		default:
			rotation.stop();
			break;
		}
	}

	/**
	 * Permet de changer le fichier que le modèle décrit. Notifie également les vues de ce changement.
	 * @param fileShow Fichier qui va être à présent utilisé.
	 */
	public void setFile(File fileShow) {
		this.fileShow = fileShow;
		
		notifyObservers("file");
	}
	
	/**
	 * Getter permettant de récupérer le fichier que le modèle décrit.
	 * @return Fichier décrit.
	 */
	public File getFile() {
		return this.fileShow;
	}

	/**
	 * Setter permettant de changer les faces décrites par le modèle.
	 * @param faces Liste de faces qui remplacera la liste actuelle.
	 */
	public void setFaces(ArrayList<Face> faces) {
		this.faces = faces;
	}

}

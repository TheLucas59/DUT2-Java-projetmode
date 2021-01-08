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

public class Modele3D extends Observed {
	private Matrix points;
	private List<Face> faces;
	private Viewer view;
	private File fileShow;
	public final Vector lumiere = new Vector(1,1,0);

	private Timeline rotation;

	public Modele3D(Matrix points, List<Face> faces, Viewer view, File fileToShow) {
		this.points = points;
		this.faces = faces;
		this.view = view;
		this.fileShow = fileToShow;

		RotationY ry = new RotationY(5);

		KeyFrame begin = new KeyFrame(Duration.seconds(0));
		KeyFrame end = new KeyFrame(Duration.millis(250), rot -> {
			this.getPoints().setMatrix(view.getCenter().multiply(ry.multiply((view.getCenter()).inv().multiply(this.getPoints()))));
			zoom();
		});

		rotation = new Timeline(begin, end);
		rotation.setCycleCount(Timeline.INDEFINITE);

		attach(view);
	}
	
	public void newView(Viewer view) {
		attach(view);
	}

	public Matrix getPoints() {
		return points;
	}

	public List<Face> getFaces() {
		return this.faces;
	}

	public void setMatrix(Matrix other) {
		this.points = other;
	}

	public void sortFaces() {
		Collections.sort(faces, new FaceComparator<Face>(this));
	}

	public void zoom() {
		view.clearScreen();
		view.getZoomText().setText("ZOOM : " + Math.round(view.getSlideZoom().getValue()) + "%");

		this.getPoints().setMatrix(view.getCenter().inv().multiply(this.getPoints()));
		Homothety h1 = new Homothety(1/view.getOldZoom());		
		this.getPoints().setMatrix(h1.multiply(this.getPoints()));

		Homothety h2 = new Homothety(view.getSlideZoom().getValue());		

		Matrix removeCenter = new Matrix(view.getCenter().multiply(h2.multiply(this.getPoints())));
		this.setMatrix(removeCenter);
		notifyObservers();
	}

	public void rotate(MouseEvent e, double oldMousePosX, double oldMousePosY) {
		double mousePosX = e.getSceneX();
		double mousePosY = e.getSceneY();

		RotationX rx = new RotationX((float) ((mousePosY - oldMousePosY)));
		RotationY ry = new RotationY((float) ((mousePosX - oldMousePosX)));

		Matrix completeRotation = new Matrix(rx.multiply(ry));
		this.getPoints().setMatrix(view.getCenter().multiply(completeRotation.multiply(view.getCenter().inv().multiply(this.getPoints()))));

		zoom();
	}

	public void translation(MouseEvent e, double oldMousePosX, double oldMousePosY) {
		double mousePosX = e.getSceneX();
		double mousePosY = e.getSceneY();

		Point pointTranslate = new Point((float) ((mousePosX - oldMousePosX)), (float) ((mousePosY - oldMousePosY)), (float) 0.0, 0); 
		Translation t = new Translation(pointTranslate);

		this.getPoints().setMatrix(view.getCenter().multiply(t.multiply(view.getCenter().inv().multiply(this.getPoints()))));

		zoom();
	}

	public float eclairageFace(Face f) {
		List<Integer> pointsFace = f.getPoints();
		float[][] pointsModele = this.points.getMatrix();
		Point p1 = new Point(pointsModele[0][pointsFace.get(0)], pointsModele[1][pointsFace.get(0)], pointsModele[2][pointsFace.get(0)], 0);
		Point p2 = new Point(pointsModele[0][pointsFace.get(1)], pointsModele[1][pointsFace.get(1)], pointsModele[2][pointsFace.get(1)], 0);
		Point p3 = new Point(pointsModele[0][pointsFace.get(2)], pointsModele[1][pointsFace.get(2)], pointsModele[2][pointsFace.get(2)], 0);
		Vector p1p2 = new Vector(p1, p2);
		Vector p1p3 = new Vector(p1, p3);
		Vector normal = p1p2.produitVectoriel(p1p3);
		normal.setX(normal.getX()/normal.norme());
		normal.setY(normal.getY()/normal.norme());
		normal.setZ(normal.getZ()/normal.norme());
		lumiere.setX(lumiere.getX()/lumiere.norme());
		lumiere.setY(lumiere.getY()/lumiere.norme());
		lumiere.setZ(lumiere.getZ()/lumiere.norme());
		float scalaire = normal.produitScalaire(lumiere);
		float teta = (float) Math.cos(scalaire);
		if(teta > 0) {
			return teta;
		}
		return -1;
	}

	public void rotationAuto(Modele3D modele, String action) {	
		switch(action) {
		case "run":
			rotation.play();
			break;
		case "stop":
			rotation.stop();
			break;
		}
	}

	public void setFile(File fileShow) {
		this.fileShow = fileShow;
		
		notifyObservers("file");
	}
	
	public File getFile() {
		return this.fileShow;
	}

	public void setFaces(ArrayList<Face> faces) {
		this.faces = faces;
	}

}

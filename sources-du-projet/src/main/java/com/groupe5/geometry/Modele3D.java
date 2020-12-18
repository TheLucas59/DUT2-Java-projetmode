package com.groupe5.geometry;

import java.util.Collections;
import java.util.List;

import com.groupe5.calculation.Homothety;
import com.groupe5.calculation.Matrix;
import com.groupe5.calculation.RotationX;
import com.groupe5.calculation.RotationY;
import com.groupe5.calculation.Translation;
import com.groupe5.view.Viewer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Modele3D {
	private Matrix points;
	private List<Face> faces;
	private Viewer view;
	
	private Timeline rotation;

	public Modele3D(Matrix points, List<Face> faces, Viewer view) {
		this.points = points;
		this.faces = faces;
		this.view = view;

		RotationY ry = new RotationY(5);

		KeyFrame begin = new KeyFrame(Duration.seconds(0));
		KeyFrame end = new KeyFrame(Duration.millis(250), rot -> {
			this.getPoints().setMatrix(view.getCenter().multiply(ry.multiply(view.getCenter().inv().multiply(this.getPoints()))));
			zoom();
		});

		rotation = new Timeline(begin, end);
		rotation.setCycleCount(Timeline.INDEFINITE);
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
		view.getZoomText().setText("ZOOM : " + Math.round(view.slideZoom.getValue()) + "%");

		this.getPoints().setMatrix(view.getCenter().inv().multiply(this.getPoints()));
		Homothety h1 = new Homothety(1/view.oldZoom);		
		this.getPoints().setMatrix(h1.multiply(this.getPoints()));

		Homothety h2 = new Homothety(view.slideZoom.getValue());		

		Matrix removeCenter = new Matrix(view.getCenter().multiply(h2.multiply(this.getPoints())));
		this.setMatrix(removeCenter);
		view.drawObject(this.getFaces(), view.showLines, view.showFaces);
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
}

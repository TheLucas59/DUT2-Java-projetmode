package com.groupe5.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import com.groupe5.calculation.Homothety;
import com.groupe5.calculation.Matrix;
import com.groupe5.calculation.RotationZ;
import com.groupe5.calculation.Translation;
import com.groupe5.geometry.Face;
import com.groupe5.geometry.Modele3D;
import com.groupe5.geometry.Point;
import com.groupe5.observerpattern.Observed;
import com.groupe5.parser.Parser;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Class qui gère la fenêtre du Viewer 3D principal
 * @author pirca
 * @author plel
 * @author duhayona
 * @author demayl
 */
public class PrimaryView extends Viewer {

	@FXML Canvas canvas;
	@FXML Button buttonOpen;
	@FXML Button buttonClose;
	@FXML AnchorPane root;
	@FXML Text loadingString;
	@FXML MenuBar menuBar;
	@FXML Region regionZoom;
	@FXML
	public Slider slideZoom;
	@FXML Text zoomText;
	
	private GraphicsContext gc;
	private static Modele3D modele;
	private static PrimaryView instance;
	private Translation center;
	private Point objectCenter;
	private double oldMousePosX;
	private double oldMousePosY;
	public double oldZoom = 1;
	
	public boolean showLines;
	public boolean showFaces;
	private boolean rotAutoFlag;
	
	public void initialize(){
		instance = this;
		
		gc = canvas.getGraphicsContext2D();
		showLines = false;
		showFaces = true;
	}
	
	/**
	 * Permet d'ouvrir le sélecteur de fichier
	 * Appelée lors d'un appuie sur le bouton "Nouveau fichier" de la vue
	 * @param e Un événement JavaFX
	 */
	public void buttonOpenFile(ActionEvent e){
		modele.rotationAuto(modele, "stop");
		ShowScene.getViewer().hide();
		ShowScene.secondWindow().hide();
		ShowScene.getFileChooser().show();
	}
	
	/**
	 * Permet de fermer le fichier ouvert
	 * @param e Un événement JavaFX
	 */
	public void buttonCloseFile(ActionEvent e){
		clearScreen();
		ShowScene.secondWindow().hide();
	}
	
	/**
	 * Méthode qui initialise les contrôles de l'objet, et qui affiche l'objet
	 * @param fileToShow Le fichier contenant les informations de l'objet à afficher
	 */
	public void showFile(File fileToShow) {
		clearScreen();

		canvas.setWidth(ShowScene.getViewer().getWidth());
		canvas.setHeight(ShowScene.getViewer().getHeight()-37);
		
		ShowScene.getViewer().setTitle("3D Viewer - " + fileToShow);
		
		center = new Translation(new Point((float) canvas.getWidth()/2, (float) canvas.getHeight()/2, 1, 1));
		
		//ACTIVATION ZOOM AUTO AVEC SLIDER

		slideZoom.setOnMouseDragged(e -> {
			modele.zoom();
			oldZoom = slideZoom.getValue();
		});

		slideZoom.setOnMouseReleased(e -> {
			modele.zoom();
			oldZoom = slideZoom.getValue();
		});

		regionZoom.setOnScroll(scroll -> {
			if(scroll.getDeltaY() > 0) {
				if(slideZoom.getValue() > 2)
					slideZoom.setValue(slideZoom.getValue()*1.05);
				else slideZoom.setValue(slideZoom.getValue() + 0.1);
				modele.zoom();
				oldZoom = slideZoom.getValue();
			}
			
			if(scroll.getDeltaY() < 0) {
				if(slideZoom.getValue() > 2)
					slideZoom.setValue(slideZoom.getValue()*0.95);
				else slideZoom.setValue(slideZoom.getValue() - 0.1);
				modele.zoom();
				oldZoom = slideZoom.getValue();
			}
		});

		regionZoom.setOnMousePressed(e -> {
				setOldAngles(e);
		});
		
		regionZoom.setOnMouseDragged(e -> {
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				modele.rotate(e, oldMousePosX, oldMousePosY);
				setOldAngles(e);
			}
			else if(e.getButton().equals(MouseButton.SECONDARY)) {
				modele.translation(e, oldMousePosX, oldMousePosY);
				setOldAngles(e);
			}
		});
		

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				rotAutoFlag = false;
				Parser p = null;
				try {
					p = new Parser(fileToShow);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
				List<Point> points = p.getPoints();
				objectCenter = setObjectCenter(points);
				
				
				for(Point pt : points) {
					pt.setX(pt.getX()-objectCenter.getX());
					pt.setY(pt.getY()-objectCenter.getY());
					pt.setZ(pt.getZ()-objectCenter.getZ());
				}
				
				List<Face> faces = new ArrayList<>();
				try {
					faces = p.getFaces(points);
				} catch (NumberFormatException | NoSuchElementException e) {
					Platform.runLater(() -> FileChooser.showAlert("Exception", "Error Message", e.getMessage()));
				}
				
				slideZoom.setValue(1);
				oldZoom = 1;
				if(modele == null)
					modele = newModele(new Matrix(points), faces, instance, fileToShow);
				else {
					modele.setMatrix(new Matrix(points));
					modele.setFaces(faces);
				}
				
				
				boolean onCenter = false;
				double vZoom = 10050;
				while(!onCenter) {
					onCenter = true;
					
					if(vZoom > 50) vZoom -= 50;
					else if(vZoom > 10) vZoom -= 10;
					else vZoom -= 0.1;
					
					Matrix tmp = modele.getPoints();
					
					Homothety h2 = new Homothety(vZoom);		
					Matrix removeCenter = new Matrix(getCenter().multiply(h2.multiply(tmp)));
					
					for(double d : removeCenter.getLineX()) {
						if(d < 25 || d > canvas.getWidth() - 25) {
							onCenter = false;
						}
					}
					
					for(double d : removeCenter.getLineY()) {
						if(d < 25 || d > canvas.getHeight() - 25) {
							onCenter = false;
						}
					}
				}
				
				slideZoom.setMax(vZoom*4);
				slideZoom.setValue(vZoom);
				
				RotationZ r = new RotationZ(180);
				modele.getPoints().setMatrix(r.multiply(modele.getPoints()));
				modele.getPoints().setMatrix(center.multiply(modele.getPoints()));
				modele.zoom();
				oldZoom = slideZoom.getValue();
			}
		});
		
		thread.setDaemon(true);
		thread.start();
	}
	
	/**
	 * Permet de cacher le modèle afficher sur la vue afin d'en afficher un nouveau
	 */
	public void clearScreen() {
		gc.setFill(Color.rgb(153, 170, 181));
		gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
	}
	
	/**
	 * Méthode utilisé lors d'une rotation, permettant de récupérer l'ancienne position de la souris avant d'effectuer la rotation
	 * @param e Un événement JavaFX permettant de récupérer les informations du pointeur de souris
	 */
	private void setOldAngles(MouseEvent e) {
		oldMousePosX = e.getSceneX();
		oldMousePosY = e.getSceneY();
	}
	
	/**
	 * Permettant de choisir si on veut afficher les lignes ou non.
	 * Cette méthode est appelée en appuyant sur le bouton "Show lines" de la vue
	 */
	public void updateShowLines() {
		showLines = !showLines;
		drawObject(modele, gc, showLines, showFaces);
	}
	
	/**
	 * Permettant de choisir si on veut afficher les couleurs faces ou non.
	 * Cette méthode est appelée en appuyant sur le bouton "Show faces" de la vue
	 */
	public void updateShowFaces() {
		showFaces = !showFaces;
		drawObject(modele, gc, showLines, showFaces);
	}
	
	/**
	 * Permet d'ouvrir la deuxième fenêtre d'affichage
	 * Cette méthode est appelée en appuyant sur le bouton "Nouvelle vue" de la vue
	 */
	public void newWindow() {
		ShowScene.secondWindow().show();
	}
	
	/**
	 * Permet de lancer la rotation automatique de l'objet
	 * Cette méthode en appuyant sur le bouton "Rotation auto" de la vue
	 */
	public void rotAuto() {
		rotAutoFlag = !rotAutoFlag;
		String action;
		
		if(rotAutoFlag) action = "run";
		else action = "stop";
		
		modele.rotationAuto(modele, action);
	}

	/**
	 * Permet de récupérer la translation qui déplace l'objet au centre
	 * @return Un objet de type Translation qui déplace l'objet au centre de l'écran
	 */
	public Translation getCenter() {
		return center;
	}

	/**
	 * Permet de récupérer la valeur affiché sur Slider gérant le zoom
	 */
	public Text getZoomText() {
		return zoomText;
	}
	
	/**
	 * Permet de récupérer la valeur du zoom de l'objet avant modification
	 * @return La valeur de l'ancien zoom
	 */
	public double getOldZoom() {
		return oldZoom;
	}
	
	/**
	 * Permet de récupérer la valeur du slider gérant le zoom
	 * @return La valeur du slider SlideZoom
	 */
	public Slider getSlideZoom() {
		return slideZoom;
	}

	/**
	 * Méthode provenant de la superclass Observer
	 * Appelée automatiquement lors d'un notifyObservers() dans Modele3D
	 */
	@Override
	public void update(Observed observed) {
		drawObject(modele, gc, showLines, showFaces);
	}

	/**
	 * Méthode provenant de la superclass Observer
	 * Appelée automatiquement lors d'un notifyObservers() dans Modele3D
	 */
	@Override
	public void update(Observed observed, Object data) {
		if(((String) data).equals("file")) {
			showFile(fileShow);
			Logger.getAnonymousLogger().info("update data");
		}
	}

	/**
	 * Permet de modifier la valeur du fichier à afficher lors du changement d'objet à afficher
	 * @param file Le nouveau fichier
	 */
	public static void setFile(File file) {
		fileShow = file;
		if(modele != null)
			modele.setFile(file);
		else
			instance.showFile(file);
	}
	
	/**
	 * Permet de récupérer le modèle afficher
	 * @return Le modèle utilisé
	 */
	public static Modele3D getModele() {
		return modele;
	}
}

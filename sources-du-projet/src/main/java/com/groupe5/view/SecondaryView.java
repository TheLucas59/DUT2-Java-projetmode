package com.groupe5.view;

import java.util.logging.Logger;

import com.groupe5.calculation.Translation;
import com.groupe5.geometry.Modele3D;
import com.groupe5.geometry.Point;
import com.groupe5.observerpattern.Observed;

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
 * Class qui gère la fenêtre du Viewer 3D secondaire
 * @author pirca
 * @author plel
 * @author duhayona
 * @author demayl
 */
public class SecondaryView extends Viewer {
	
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
	private Modele3D modele;
	private static SecondaryView instance;
	private Translation center;
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
		
		showFile(PrimaryView.getModele());
	}

	/**
	 * Méthode qui initialise les contrôles de l'objet, et qui affiche l'objet
	 * @param modele3d L'objet à afficher
	 */
	public void showFile(Modele3D modele3d) {
		
		instance = this;
		
		clearScreen();

		canvas.setWidth(ShowScene.getViewer().getWidth());
		canvas.setHeight(ShowScene.getViewer().getHeight()-37);
		
		ShowScene.getViewer().setTitle("3D Viewer - " + fileShow.getName());
		
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
				if(modele == null)
					modele = PrimaryView.getModele();
				else
					modele = modele3d;
				modele.newView(instance);
				clearScreen();
				modele.notifyObservers();
			}
		});
		
		thread.setDaemon(true);
		thread.start();
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
	 * Permet de récupérer la translation qui déplace l'objet au centre
	 * @return Un objet de type Translation qui déplace l'objet au centre de l'écran
	 */
	@Override
	public Translation getCenter() {
		return center;
	}

	/**
	 * Permet de cacher le modèle afficher sur la vue afin d'en afficher un nouveau
	 */
	@Override
	public void clearScreen() {
		gc.setFill(Color.rgb(153, 170, 181));
		gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
	}
	
	/**
	 * Permet de récupérer la valeur affiché sur Slider gérant le zoom
	 */
	@Override
	public Text getZoomText() {
		return zoomText;
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
	 * Permet de lancer la rotation automatique de l'objet
	 * Cette méthode est appelée en appuyant sur le bouton "Rotation auto" de la vue
	 */
	public void rotAuto() {
		rotAutoFlag = !rotAutoFlag;
		String action;
		
		if(rotAutoFlag) action = "run";
		else action = "stop";
		
		modele.rotationAuto(modele, action);
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
			Logger.getAnonymousLogger().info("update data 2");
			showFile(PrimaryView.getModele());	
		}
	}
}

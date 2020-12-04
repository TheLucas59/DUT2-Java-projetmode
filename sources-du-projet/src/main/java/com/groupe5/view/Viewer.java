package com.groupe5.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.groupe5.calculation.Homothety;
import com.groupe5.calculation.Matrix;
import com.groupe5.calculation.RotationX;
import com.groupe5.calculation.RotationY;
import com.groupe5.calculation.RotationZ;
import com.groupe5.calculation.Translation;
import com.groupe5.geometry.Face;
import com.groupe5.geometry.Modele3D;
import com.groupe5.geometry.Point;
import com.groupe5.parser.Parser;

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

public class Viewer{

	@FXML Canvas canvas;
	@FXML Button buttonOpen;
	@FXML Button buttonClose;
	@FXML AnchorPane root;
	@FXML Text loadingString;
	@FXML MenuBar menuBar;
	@FXML Region regionZoom;
	@FXML Slider slideZoom;
	@FXML Text zoomText;
	
	private GraphicsContext gc;
	private Modele3D modele;
	private int size;
	private static Viewer instance;
	private Translation center;
	@SuppressWarnings("unused")
	private Point objectCenter;
	private double oldMousePosX;
	private double oldMousePosY;
	private double oldZoom = 1;
	
	boolean showLines;
	boolean showFaces;
	
	
	public void initialize(){
		// System.out.println("init viewer");
		instance = this;
		
		gc = canvas.getGraphicsContext2D();
		showLines = true;
		showFaces = false;
	}
	
	public void buttonOpenFile(ActionEvent e){
		ShowScene.getViewer().hide();
		ShowScene.getFileChooser().show();
	}
	
	public void buttonCloseFile(ActionEvent e){
		clearScreen();
		// System.out.println("closeFile");
	}
	
	public void showFile(File fileToShow) {
		clearScreen();

		canvas.setWidth(ShowScene.getViewer().getWidth());
		canvas.setHeight(ShowScene.getViewer().getHeight()-37);
		
		ShowScene.getViewer().setTitle("3D Viewer - "+fileToShow.getName());
		
		center = new Translation(new Point(((float) canvas.getWidth()/2), ((float) canvas.getHeight()/2), 1, 1));
		
		//ACTIVATION ZOOM AUTO AVEC SLIDER

		slideZoom.setOnMouseDragged(e -> {
			zoom();
			oldZoom = slideZoom.getValue();
		});

		slideZoom.setOnMouseReleased(e -> {
			zoom();
			oldZoom = slideZoom.getValue();
		});

		regionZoom.setOnScroll(scroll -> {
			if(scroll.getDeltaY() > 0) {
				slideZoom.setValue(slideZoom.getValue() + 1);
				zoom();
				oldZoom = slideZoom.getValue();
			}
			
			if(scroll.getDeltaY() < 0) {
				slideZoom.setValue(slideZoom.getValue() - 1);
				zoom();
				oldZoom = slideZoom.getValue();
			}
		});

		regionZoom.setOnMousePressed(e -> {
				setOldAngles(e);
		});
		
		regionZoom.setOnMouseDragged(e -> {
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				rotate(e);
				setOldAngles(e);
			}
			else if(e.getButton().equals(MouseButton.SECONDARY)) {
				translation(e);
				setOldAngles(e);
			}
		});
		

		Thread thread = new Thread(new Runnable() {
			private boolean reduce = false;
			private boolean zoom = false;
			private int iRed = 0;
			private int iZoom = 0;

			@Override
			public void run() {
				Parser p = null;
				try {
					p = new Parser(fileToShow);
				}
				catch (IOException e) {}
				
				ArrayList<Point> points = p.getPoints();
				objectCenter = setObjectCenter(points);
				
				
				for(Point pt : points) {
					pt.setX(pt.getX()-objectCenter.getX());
					pt.setY(pt.getY()-objectCenter.getY());
					pt.setZ(pt.getZ()-objectCenter.getZ());
					
					if(pt.getX() > 100) iRed++;
					else if(pt.getY() > 100) iRed++;
					if(pt.getX() < 1) iZoom++;
					else if(pt.getY() < 1) iZoom++;
				}
				
				if(iRed >= points.size()*0.95) reduce = true;
				if(iZoom >= points.size()*0.95) zoom = true;
				
				if(reduce) {					
					for(Point pt : points) {
						pt.setX(pt.getX()/10);
						pt.setY(pt.getY()/10);
						pt.setZ(pt.getZ()/10);
					}
				}
				
				if(zoom) {					
					for(Point pt : points) {
						pt.setX(pt.getX()*20);
						pt.setY(pt.getY()*20);
						pt.setZ(pt.getZ()*20);
					}
				}
				
				
				ArrayList<Face> faces = p.getFaces(points);
				
				slideZoom.setValue(1);
				oldZoom = 1;
				modele = new Modele3D(new Matrix(points), faces);
				
				RotationZ r = new RotationZ(180);
				modele.getPoints().setMatrix(r.multiply(modele.getPoints()));
				modele.getPoints().setMatrix(center.multiply(modele.getPoints()));
				drawObject(modele.getFaces(), showLines, showFaces);
			}
		});
		
		thread.setDaemon(true);
		thread.start();
	}
	
	public void clearScreen() {
		gc.setFill(Color.rgb(153, 170, 181));
		gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
	}
	
	public static void setFile(File fileToShow){
		instance.showFile(fileToShow);
	}
	
	public void zoom() {
		zoomText.setText("ZOOM : " + Math.round(slideZoom.getValue()) + "%");
		clearScreen();
		
		modele.getPoints().setMatrix(center.inv().multiply(modele.getPoints()));
		Homothety h1 = new Homothety(1/oldZoom);		
		modele.getPoints().setMatrix(h1.multiply(modele.getPoints()));
		
		Homothety h2 = new Homothety(slideZoom.getValue());		
		
		Matrix removeCenter = new Matrix(center.multiply(h2.multiply(modele.getPoints())));
		modele.setMatrix(removeCenter);
		drawObject(modele.getFaces(), showLines, showFaces);
	}
	
	private void setOldAngles(MouseEvent e) {
		oldMousePosX = e.getSceneX();
		oldMousePosY = e.getSceneY();
	}
	
	public void rotate(MouseEvent e) {
		clearScreen();
		
		double mousePosX = e.getSceneX();
		double mousePosY = e.getSceneY();
		
		RotationX rx = new RotationX((float) ((mousePosY - oldMousePosY)));
		RotationY ry = new RotationY((float) ((mousePosX - oldMousePosX)));
		
		Matrix completeRotation = new Matrix(rx.multiply(ry));
		modele.getPoints().setMatrix(center.multiply(completeRotation.multiply(center.inv().multiply(modele.getPoints()))));
		
		zoom();
	}
	
	public void translation(MouseEvent e) {
		double mousePosX = e.getSceneX();
		double mousePosY = e.getSceneY();
		
		Point pointTranslate = new Point((float) ((mousePosX - oldMousePosX)), (float) ((mousePosY - oldMousePosY)), (float) 0.0, 0); 
		Translation t = new Translation(pointTranslate);
		
		modele.getPoints().setMatrix(center.multiply(t.multiply(center.inv().multiply(modele.getPoints()))));
		
		zoom();
	}
	
	public Point setObjectCenter(ArrayList<Point> points) {
		double X = 0, Y = 0, Z = 0;
		int size = points.size();
		
		for(Point p : points) {
			X += p.getX();
			Y += p.getY();
			Z += p.getZ();
		}
		
		return new Point(((float) X/size), ((float) Y/size), ((float) Z/size), 0);
	}
	
	public void drawObject(List<Face> faces, boolean showLines, boolean showFaces) {	
		clearScreen();
		
		modele.sortFaces();
		gc.setStroke(Color.GRAY);
		gc.setLineWidth(0.5);
		gc.setFill(Color.LIGHTGRAY);
		
		for(Face f : faces) {
			double[] pointsX = getCoordsX(f);
			double[] pointsY = getCoordsY(f);
			if(showFaces)
				gc.fillPolygon(pointsX, pointsY, f.getNbPoints()-1);
			if(showLines)
				gc.strokePolygon(pointsX, pointsY, f.getNbPoints()-1);
		}
	}
	
	private double[] getCoordsX(Face f) {
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

	private double[] getCoordsY(Face f) {
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
	
	public void updateShowLines() {
		showLines = !showLines;
		drawObject(modele.getFaces(), showLines, showFaces);
	}
	
	public void updateShowFaces() {
		showFaces = !showFaces;
		drawObject(modele.getFaces(), showLines, showFaces);
	}
	
	public void center() {
		Translation t = new Translation(objectCenter);
		modele.getPoints().setMatrix(t.multiply(modele.getPoints()));
	}
}

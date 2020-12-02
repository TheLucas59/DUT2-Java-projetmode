package com.groupe5.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.groupe5.calculation.Homothety;
import com.groupe5.calculation.Matrix;
import com.groupe5.calculation.RotationX;
import com.groupe5.calculation.RotationY;
import com.groupe5.calculation.RotationZ;
import com.groupe5.calculation.Translation;
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
	private Matrix m;
	private int size;
	private static Viewer instance;
	private Translation center;
	@SuppressWarnings("unused")
	private Point objectCenter;
	private double oldMousePosX;
	private double oldMousePosY;
	
	public void initialize(){
		// System.out.println("init viewer");
		instance = this;
		
		gc = canvas.getGraphicsContext2D();
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
		});

		slideZoom.setOnMouseReleased(e -> {
			zoom();
		});

		regionZoom.setOnScroll(scroll -> {
			if(scroll.getDeltaY() > 0) {
				slideZoom.setValue(slideZoom.getValue() + 1);
				zoom();
			}
			
			if(scroll.getDeltaY() < 0) {
				slideZoom.setValue(slideZoom.getValue() - 1);
				zoom();
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
			@Override
			public void run() {
				Parser p = null;
				try {
					p = new Parser(fileToShow);
				}
				catch (IOException e) {}
				
				ArrayList<Point> points = p.getPoints();
				size = points.size();
				m = new Matrix(points);
				objectCenter = setObjectCenter(points);
				RotationZ r = new RotationZ(180);
				m.setMatrix(r.multiply(m));
				m.setMatrix(center.multiply(m));
				projection(m);
				drawObject(m.getLineX(), m.getLineY(), size);
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
		System.out.println("chargement "+fileToShow.getName());
		instance.showFile(fileToShow);
		System.out.println("chargement terminé");
	}
	
	public void zoom() {
		zoomText.setText("ZOOM : " + Math.round(slideZoom.getValue()) + "%");
		clearScreen();
		
		Homothety h = new Homothety(slideZoom.getValue());		
		Matrix tmp = new Matrix(center.inv().multiply(m));		
		
		Matrix reMoveCenter = new Matrix(center.multiply(h.multiply(tmp)));
		drawObject(reMoveCenter.getLineX(), reMoveCenter.getLineY(), size);
	}
	
	private void setOldAngles(MouseEvent e) {
		oldMousePosX = e.getSceneX();
		oldMousePosY = e.getSceneY();
	}
	
	public void rotate(MouseEvent e) {
		clearScreen();
		
		double mousePosX = e.getSceneX();
		double mousePosY = e.getSceneY();
		
		RotationX rx = new RotationX((float) ((mousePosY - oldMousePosY)/(slideZoom.getValue()/100)));
		RotationY ry = new RotationY((float) ((mousePosX - oldMousePosX)/(slideZoom.getValue()/100)));
		
		Matrix completeRotation = new Matrix(rx.multiply(ry));
		m.setMatrix(center.multiply(completeRotation.multiply(center.inv().multiply(m))));
		
		zoom();
	}
	
	public void projection(Matrix m) {
		float[][] matrix = m.getMatrix();
		
		final float COEFF_PROJECTION = 1500;
		
		for(int i = 0; i < matrix[0].length; i++) {
			float z = COEFF_PROJECTION/(COEFF_PROJECTION+matrix[2][i]);
			
			matrix[0][i] = matrix[0][i] * z; 
			matrix[1][i] = matrix[1][i] * z; 
		}
		
		m.setMatrix(matrix);
	}
	
	public void translation(MouseEvent e) {
		double mousePosX = e.getSceneX();
		double mousePosY = e.getSceneY();
		
		Point pointTranslate = new Point((float) ((mousePosX - oldMousePosX)/slideZoom.getValue()), (float) ((mousePosY - oldMousePosY)/slideZoom.getValue()), (float) 0.0, 0); 
		Translation t = new Translation(pointTranslate);
		
		m.setMatrix(center.multiply(t.multiply(center.inv().multiply(m))));
		
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
	
	public void drawObject(double[] X, double[] Y, int nb_points) {	
		gc.strokePolygon(X, Y, nb_points);
	}

}

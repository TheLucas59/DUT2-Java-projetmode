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
	private Point objectCenter;
	
	public void initialize(){
		System.out.println("init viewer");
		instance = this;
		
		gc = canvas.getGraphicsContext2D();
	}
	
	public void buttonOpenFile(ActionEvent e){
		ShowScene.getViewer().hide();
		ShowScene.getFileChooser().show();
	}
	
	public void buttonCloseFile(ActionEvent e){
		clearScreen();
		System.out.println("closeFile");
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

		regionZoom.setOnMouseDragged(e -> {
			rotate();
		});
		
		regionZoom.setOnMouseReleased(e -> {
			rotate();
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

				showObject(m.getLineX(), m.getLineY(), size);
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
		zoomText.setText("ZOOM : " + slideZoom.getValue());
		clearScreen();
		
		Homothety h = new Homothety(slideZoom.getValue());		
		Matrix tmp = new Matrix(center.inv().multiply(m));		
		
		Matrix reMoveCenter = new Matrix(center.multiply(h.multiply(tmp)));
		showObject(reMoveCenter.getLineX(), reMoveCenter.getLineY(), size);
	}
	
	public void rotate() {
		System.out.println("rotation");
		clearScreen();
		
		RotationX rx = new RotationX(30);
		RotationY ry = new RotationY(30);
		RotationZ rz = new RotationZ(30);
		
		Matrix completeRotation = new Matrix(new Matrix(rx.multiply(ry)).multiply(rz));
		m.setMatrix(m.multiply(completeRotation));
		
		gc.strokePolygon(m.getLineX(), m.getLineY(), size);
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
	
	public void showObject(double[] X, double[] Y, int nb_points) {
		gc.strokePolygon(X, Y, nb_points);
	}

}

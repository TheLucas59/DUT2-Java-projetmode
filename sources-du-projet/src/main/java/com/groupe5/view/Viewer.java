package com.groupe5.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.groupe5.calculation.Homothety;
import com.groupe5.calculation.Matrix;
import com.groupe5.calculation.RotationZ;
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
	@FXML Button testCanvas;
	@FXML Button testCow;
	@FXML Text zoomText;
	
	private GraphicsContext gc;
	
	private Matrix m;
	private int size;
	
	public static double widthCanvas, heightCanvas;
	
	private static Viewer instance;
	
	public void initialize(){
		System.out.println("init viewer");
		instance = this;
		
		gc = canvas.getGraphicsContext2D();
		
		testCanvas.setOnAction(a -> {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.setFill(Color.RED);
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		});

		testCow.setOnAction(a -> {
			showFile(new File("./exemples/cow.ply"));
		});
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
		
		widthCanvas = canvas.getWidth();
		heightCanvas = canvas.getHeight();
		
		//ACTIVATION ZOOM AUTO AVEC SLIDER

		slideZoom.setOnMouseDragged(e -> {
			zoomText.setText("ZOOM : " + slideZoom.getValue());
			clearScreen();
			Homothety h = new Homothety(slideZoom.getValue());
			Matrix m_zoom = new Matrix(m.multiply(h));
			gc.strokePolygon(m_zoom.getLineX(), m_zoom.getLineY(), size);
		});

		slideZoom.setOnMouseReleased(e -> {
			zoomText.setText("ZOOM : " + slideZoom.getValue());
			clearScreen();
			Homothety h = new Homothety(slideZoom.getValue());
			Matrix m_zoom = new Matrix(m.multiply(h));
			gc.strokePolygon(m_zoom.getLineX(), m_zoom.getLineY(), size);
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
				
				m.setMatrix(m.multiply(new RotationZ(180)));

				gc.strokePolygon(m.getLineX(), m.getLineY(), size);
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

}

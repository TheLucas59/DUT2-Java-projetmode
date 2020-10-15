package com.groupe5.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.groupe5.calculation.Homothety;
import com.groupe5.calculation.Matrix;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sql.rowset.spi.TransactionalWriter;

public class Controller {

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
	
	final String PATH = "./exemples/";
	private static Stage stage;

	private Matrix m;
	private int size;
	
	public static void setStage(Stage s) {
		stage = s;
	}

	public void initialize(){
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
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open file : ");		
		fileChooser.setInitialDirectory(new File(PATH));
		
		//set Extension Filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PLY Files (*.ply)", "*.ply");
		fileChooser.getExtensionFilters().add(extFilter);		
		
		File fileToShow = fileChooser.showOpenDialog(root.getScene().getWindow());
		
		showFile(fileToShow);
		stage.setTitle("3D Viewer - " + fileToShow.getName());
	}
	
	public void buttonCloseFile(ActionEvent e){
		clearScreen();
		
		stage.setTitle("3D Viewer");
	}
	
	public void showFile(File fileToShow) {

		canvas.setWidth(stage.getWidth());
		canvas.setHeight(stage.getHeight()-37);
		
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
}

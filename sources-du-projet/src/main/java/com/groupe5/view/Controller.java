package com.groupe5.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

	@FXML Canvas canvas;
	@FXML Button buttonOpen;
	@FXML Button buttonClose;
	@FXML AnchorPane root;
	@FXML Text loadingString;
	@FXML MenuBar menuBar;
	@FXML Region regionZoom;
	@FXML Slider slideZoom;
	
	private GraphicsContext gc;
	
	final String PATH = "./exemples/";
	private static Stage stage;

	double cursorX, cursorY;
	
	public static void setStage(Stage s) {
		stage = s;
	}

	public void initialize(){
		
		gc = canvas.getGraphicsContext2D();
		
		gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		regionZoom.setOnScroll(e -> {
			if(e.getDeltaY() > 0) {
				canvas.getGraphicsContext2D().scale(10, 10);
			}
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
		gc.restore();
		stage.setTitle("3D Viewer");
	}
	
	public void showFile(File fileToShow) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Parser p = null;
				try {
					p = new Parser(fileToShow);
				}
				catch (IOException e) {}
				
				
				final int ZOOM = 150;
				
				ArrayList<Point> points = p.getPoints();
				int size = points.size();
				double pointsX[] = new double[size];
				double pointsY[] = new double[size];
				for(int i = 0; i < size; i++) {
					pointsX[i] = points.get(i).getX() * ZOOM;
					pointsY[i] = points.get(i).getY() * ZOOM;
				}
				
				gc.strokePolygon(pointsX, pointsY, size);
				
//				canvas.setTranslateX(300);
//				canvas.setTranslateY(150);
			}
		});
		
		thread.setDaemon(true);
		thread.start();
	}
}

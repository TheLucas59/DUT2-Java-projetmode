package com.groupe5.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.groupe5.geometry.Point;
import com.groupe5.parser.Parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

	@FXML MeshView meshView;
	@FXML Button buttonOpen;
	@FXML Button buttonClose;
	@FXML AnchorPane root;
	@FXML Text loadingString;
	@FXML MenuBar menuBar;
	@FXML Region regionZoom;
	@FXML Slider slideZoom;
	
	final String PATH = "./exemples/";
	private static Stage stage;

	double cursorX, cursorY;
	
	public static void setStage(Stage s) {
		stage = s;
	}

	public void initialize(){
		
		regionZoom.setOnScroll(scroll -> {
			
			System.out.println(meshView.getScaleX() + " - " + meshView.getScaleY());
			if(scroll.getDeltaY() > 0) {
				meshView.setScaleX(meshView.getScaleX()+slideZoom.getValue());
				meshView.setScaleY(meshView.getScaleY()+slideZoom.getValue());
			}
			else if(scroll.getDeltaY() < 0 && meshView.getScaleX() > 1 && meshView.getScaleY() > 1) {
				meshView.setScaleX(meshView.getScaleX()-slideZoom.getValue());
				meshView.setScaleY(meshView.getScaleY()-slideZoom.getValue());
			}
		});
		

		regionZoom.setOnMouseDragEntered(drag -> {
			cursorX = drag.getX();
			cursorY = drag.getY();
			
			System.out.println(cursorX);
			System.out.println(cursorY);
		});
		
		regionZoom.setOnMouseDragged(drag -> {
			if(cursorY > drag.getY()) meshView.setRotate(meshView.getRotate()-1);
			else if(cursorY < drag.getY()) meshView.setRotate(meshView.getRotate()+1);
		});
	}
		
	public void buttonOpenFile(ActionEvent e){
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open file : ");		
		fileChooser.setInitialDirectory(new File(PATH));
		
		//set Extension Filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PLY Files (*.ply)", "*.ply");
		fileChooser.getExtensionFilters().add(extFilter);		
		
		File fileToShow = fileChooser.showOpenDialog(meshView.getScene().getWindow());


		Thread showMesh = new Thread(new Runnable() {
			@Override
			public void run() {
				Parser p = null;
				
				loadingString.setVisible(true);

				try {
					p = new Parser(fileToShow);
				} catch (IOException ioException) {}
				
				TriangleMesh mesh = new TriangleMesh();

				mesh.getTexCoords().addAll(0, 0);
				
				// Side
				ArrayList<Point> points = p.getPoints();
				for (int i = 0; i < points.size(); i++) {
					mesh.getPoints().addAll(points.get(i).getX(), points.get(i).getY(), points.get(i).getZ());
				}
				
				p.getFaces(points);
				for (int i = 0; i < p.getIdPoints().length; i++) {
					int[] idPoint = p.getIdPoints();
					mesh.getFaces().addAll(idPoint[i],0);
				}
				
				
				meshView.setMesh(mesh);
				
				loadingString.setVisible(false);
			}
		});

		meshView.setCullFace(CullFace.FRONT);
		meshView.setDrawMode(DrawMode.FILL);
		meshView.setTranslateX(318);
		meshView.setTranslateY(120);
		meshView.setScaleX(2);
		meshView.setScaleY(2);

		showMesh.start();
		stage.setTitle("3D Viewer - "+fileToShow.getName());
	}
	
	public void buttonCloseFile(ActionEvent e){
		meshView.setMesh(null);
		stage.setTitle("3D Viewer");
	}
	
}

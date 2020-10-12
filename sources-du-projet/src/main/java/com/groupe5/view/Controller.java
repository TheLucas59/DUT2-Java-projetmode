package com.groupe5.view;

import java.io.File;
import java.io.IOException;

import com.groupe5.parser.Parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class Controller {

	@FXML MeshView meshView;
	@FXML Button buttonOpen;
	@FXML Button buttonClose;
	@FXML AnchorPane root;
	@FXML Text loadingString;
	@FXML MenuBar menuBar;
	@FXML Region regionZoom;
	final String PATH = "./exemples/";

	double cursorX, cursorY;

	public void initialize(){
		
		regionZoom.setOnScroll(scroll -> {
			
			System.out.println(meshView.getScaleX() + " - " + meshView.getScaleY());
			if(scroll.getDeltaY() > 0) {
				meshView.setScaleX(meshView.getScaleX()+1);
				meshView.setScaleY(meshView.getScaleY()+1);
			}
			else if(scroll.getDeltaY() < 0 && meshView.getScaleX() > 1 && meshView.getScaleY() > 1) {
				meshView.setScaleX(meshView.getScaleX()-1);
				meshView.setScaleY(meshView.getScaleY()-1);
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
		
		FileChooser file = new FileChooser();
		file.setTitle("Open file : ");		
		file.setInitialDirectory(new File(PATH));
		
		//set Extension Filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PLY Files (*.ply)", "*.ply");
		file.getExtensionFilters().add(extFilter);		
		
		File fileToShow = file.showOpenDialog(meshView.getScene().getWindow());


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

				for (int i = 0; i < p.getPoints().size(); i++) {
					mesh.getPoints().addAll(p.getPoints().get(i).getX(), p.getPoints().get(i).getY(), p.getPoints().get(i).getZ());
				}
				
				p.getFaces(p.getPoints());
				for (int i = 0; i < p.getIdPoints().length; i++) {
					int[] idPoint = p.getIdPoints();
					mesh.getFaces().addAll(idPoint[i],0);
				}
				
				
				meshView.setMesh(mesh);
				
				loadingString.setVisible(false);
			}
		});

		meshView.setCullFace(CullFace.FRONT);
		meshView.setDrawMode(DrawMode.LINE);
		meshView.setTranslateX(318);
		meshView.setTranslateY(120);
		meshView.setScaleX(2);
		meshView.setScaleY(2);

		showMesh.start();
	}
	
	public void buttonCloseFile(ActionEvent e){
		meshView.setMesh(null);
	}
	
}

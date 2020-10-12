package com.groupe5.view;

import java.io.File;
import java.io.IOException;

import com.groupe5.parser.Parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
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
	@FXML ProgressBar loadingBar;
	@FXML MenuBar menuBar;

	final String PATH = "./exemples/";

	public void initialize(){
		meshView.setOnScroll(scroll -> {
			
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

		meshView.setOnMouseDragged(drag -> {
			meshView.setRotate(meshView.getRotate()+1);
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
				loadingBar.setVisible(true);

				try {
					p = new Parser(fileToShow);
				} catch (IOException ioException) {}
				
				loadingBar.setProgress(25);


				TriangleMesh mesh = new TriangleMesh();

				mesh.getTexCoords().addAll(0, 0);
				
				loadingBar.setProgress(50);

				// Side

				for (int i = 0; i < p.getPoints().size(); i++) {
					mesh.getPoints().addAll(p.getPoints().get(i).getX(), p.getPoints().get(i).getY(), p.getPoints().get(i).getZ());
				}
				
				loadingBar.setProgress(75);

				p.getFaces(p.getPoints());
				for (int i = 0; i < p.getIdPoints().length; i++) {
					int[] idPoint = p.getIdPoints();
					mesh.getFaces().addAll(idPoint[i],0);
				}
				
				loadingBar.setProgress(100);
				
				meshView.setMesh(mesh);
				
				loadingString.setVisible(false);
				loadingBar.setVisible(false);
			}
		});

		meshView.setDrawMode(DrawMode.FILL);
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

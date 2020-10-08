package com.groupe5.view;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.MeshView;
import javafx.stage.FileChooser;

public class Controller {

	@FXML MeshView meshView;
	@FXML Button buttonOpen;
	@FXML Button buttonClose;

	final String PATH = "./exemples/";
		
	public void buttonOpenFile(ActionEvent e){
		
		FileChooser file = new FileChooser();
		file.setTitle("Open file : ");		
		file.setInitialDirectory(new File(PATH));
		
		//set Extension Filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PLY Files (*.ply)", "*.ply");
		file.getExtensionFilters().add(extFilter);		
		
		@SuppressWarnings("unused")
		File fileToShow = file.showOpenDialog(meshView.getScene().getWindow());	
	}
	
	public void buttonCloseFile(ActionEvent e){
	}
	
}

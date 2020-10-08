package com.groupe5.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Popup;
import javafx.stage.PopupWindow.AnchorLocation;

public class Controller {

	@FXML Canvas canvas;
	@FXML Button buttonOpen;
	@FXML Button buttonClose;

	Popup p;
	
	ListView<String> plyFiles;
	
	final String PATH = "./exemples/";
	
	
	public void initialize() {
		plyFiles = new ListView<String>();
		plyFiles.setOnMouseClicked(e -> {
			String modelName =  PATH + plyFiles.getSelectionModel().getSelectedItem();		
			System.out.println(modelName);
		});		

	}
		
	public void buttonOpenFile(ActionEvent e){

		p = new Popup();

		ListPLY files = new ListPLY(PATH);
		plyFiles.setItems(files.getFiles());

		p.getContent().add(plyFiles);
		
		p.setAnchorLocation(AnchorLocation.CONTENT_TOP_RIGHT);
		
		p.setAutoHide(true);

		p.show(canvas.getParent().getScene().getWindow(), p.getAnchorX(), p.getAnchorY());
		
	}
	
	public void buttonCloseFile(ActionEvent e){
		p.hide();
	}
	
}

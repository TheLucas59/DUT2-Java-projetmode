package com.groupe5.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.stage.Popup;
import javafx.stage.PopupWindow.AnchorLocation;


public class Controller {

	@FXML Canvas canvas;

	Popup p;
	
	ListView<String> plyFiles;
	
	final String PATH = "./exemples";
	
	
	public void initialize() {
		
	}
	
	
	public void buttonOpenFile(ActionEvent e){

		p = new Popup();

		ListPLY files = new ListPLY(PATH);
		plyFiles = new ListView<String>(files.getFiles());

		p.getContent().add(plyFiles);
		
		p.setAnchorLocation(AnchorLocation.CONTENT_TOP_RIGHT);
		
		p.setAutoHide(true);

		p.show(canvas.getParent().getScene().getWindow(), p.getAnchorX(), p.getAnchorY());
		
	}
	
	public void buttonCloseFile(ActionEvent e){
		p.hide();
	}
	
}

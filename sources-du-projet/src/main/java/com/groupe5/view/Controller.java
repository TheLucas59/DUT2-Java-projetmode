package com.groupe5.view;

import java.io.File;
import java.io.IOException;

import com.groupe5.parser.Parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class Controller {

	@FXML Canvas canvas;
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
		
		File fileToShow = file.showOpenDialog(canvas.getScene().getWindow());	
		
		try {
			Parser p = new Parser(fileToShow);
		} catch (IOException e1) {}
		
		canvas.getGraphicsContext2D().fillPolygon(new double[] {100,  150,  200, 2} , new double[] {205, 0, 300, 2}, 4);
	}
	
	public void buttonCloseFile(ActionEvent e){
		canvas.getGraphicsContext2D().restore();
	}
	
}

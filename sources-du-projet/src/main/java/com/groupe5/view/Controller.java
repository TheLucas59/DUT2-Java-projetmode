package com.groupe5.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

public class Controller {

	@FXML Canvas canvas;
	@FXML Button buttonOpen;
	@FXML Button buttonClose;
	
	public void buttonOpenFile(ActionEvent e){
		System.out.println("aaaaaa");
	}
	
	public void buttonCloseFile(ActionEvent e){
		System.out.println("bbbbbb");
	}
	
}

package com.groupe5.view;

import java.io.File;
import java.util.ArrayList;

import com.groupe5.parser.PLYFile;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;

public class FileChooser {
	@FXML TextField path;
	@FXML TableView<PLYFile> tableview;
	@FXML TableColumn<PLYFile, String> fileName;
	@FXML TableColumn<PLYFile, String> fileComment;
	@FXML TableColumn<PLYFile, Integer> filePoints;
	@FXML TableColumn<PLYFile, Integer> fileFaces;
	@FXML TableColumn<PLYFile, String> fileFormat;


	public void initialize(){
		fileName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFile().getName()));
		fileComment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));
		filePoints.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTotalPoints()).asObject());
		fileFaces.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTotalFaces()).asObject());
		fileFormat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormat()));

		tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tableview.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent k) {
				if (k.getCode().equals(KeyCode.ENTER)) {
					ShowScene.getFileChooser().hide();
					ShowScene.getViewer().show();
					Viewer.setFile(tableview.getSelectionModel().getSelectedItem().getFile());
				}
			}
		});
		
		path.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent k) {
				if(k.getCode().equals(KeyCode.ENTER)){
					File dir = new File(path.getText());
					if(dir.exists()){
						showFiles(dir);
					}else{
						showAlert("3D Viewer", "Erreur", "Dossier non trouvé");
					}
				}
			}
		});
		
		
	}

	public void pathButton(ActionEvent e){
		DirectoryChooser directoryChooser = new DirectoryChooser();
		if(path.getText().length() != 0 && new File(path.getText()).exists()) directoryChooser.setInitialDirectory(new File(path.getText()));
		else if(new File("./exemples/").exists()) directoryChooser.setInitialDirectory(new File("./exemples/"));
		File selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory == null) return;
		path.setText(selectedDirectory.getAbsolutePath());
		showFiles(selectedDirectory);
	}

	public void showFiles(File selectedDirectory) {
		ArrayList<PLYFile> list = new ArrayList<PLYFile>();
		for(File f : selectedDirectory.listFiles()){
			if(f.getName().endsWith(".ply")){
				PLYFile ply = new PLYFile(f);
				if(ply.onlyHeader()) list.add(ply);
			}
		}
		tableview.setItems(FXCollections.observableArrayList(list));
	}
	
	public void showAlert(String title, String headerText, String contentText){
		Alert alert = new Alert(AlertType.ERROR);

		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		alert.showAndWait();
	}
}
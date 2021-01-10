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
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

/**
 * Class pour le sélecteur de fichiers
 * @author pirca
 */
public class FileChooser {
	@FXML TextField path;
	@FXML TableView<PLYFile> tableview;
	@FXML TableColumn<PLYFile, String> fileName;
	@FXML TableColumn<PLYFile, String> fileComment;
	@FXML TableColumn<PLYFile, Integer> filePoints;
	@FXML TableColumn<PLYFile, Integer> fileFaces;
	@FXML TableColumn<PLYFile, String> fileFormat;
	@FXML TextField fileSearch;
	
	private PLYFile selectedItem = null;
	private ArrayList<PLYFile> listFile = new ArrayList<>();

	/**
	 * Fonction qui initialise les évenements du sélecteur de fichiers
	 */
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
					selectedItem = tableview.getSelectionModel().getSelectedItem();
					if(selectedItem != null) openFile();
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
		
		tableview.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event){
				if(selectedItem == tableview.getSelectionModel().getSelectedItem()){
					if(selectedItem != null) openFile();
				}else{
					selectedItem = tableview.getSelectionModel().getSelectedItem();
				}
			}
		});
		
		fileSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event){
				showFilesSearch();
			}
		});
	}

	/**
	 * ouvrir un fichier dans le viewer
	 */
	private void openFile(){
		ShowScene.getFileChooser().hide();
		ShowScene.getViewer().show();
		PrimaryView.setFile(selectedItem.getFile());
	}
	
	/**
	 * Gère l'evenement du bouton pour choisir l'emplacement des fichiers
	 * @param e ActionEvent de JavaFX
	 */
	public void pathButton(ActionEvent e){
		DirectoryChooser directoryChooser = new DirectoryChooser();
		if(path.getText().length() != 0 && new File(path.getText()).exists()) directoryChooser.setInitialDirectory(new File(path.getText()));
		else if(new File("./exemples/").exists()) directoryChooser.setInitialDirectory(new File("./exemples/"));
		File selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory == null) return;
		path.setText(selectedDirectory.getAbsolutePath());
		showFiles(selectedDirectory);
	}

	/**
	 * Affiche les fichiers du répértoire choisi
	 * @param selectedDirectory dossier choisi
	 */
	public void showFiles(File selectedDirectory) {
		fileSearch.clear();
		listFile.clear();
		for(File f : selectedDirectory.listFiles()){
			if(f.getName().endsWith(".ply")){
				PLYFile ply = new PLYFile(f);
				if(ply.onlyHeader()) {
					listFile.add(ply);
				}
			}
		}
		tableview.setItems(FXCollections.observableArrayList(listFile));
	}
	
	/**
	 * Affiche les fichiers grâce à la recherche
	 */
	public void showFilesSearch(){
		tableview.getItems().clear();
		if(fileSearch.getText().isEmpty()){
			tableview.setItems(FXCollections.observableArrayList(listFile));
			return;
		}
		ArrayList<PLYFile> accepted = new ArrayList<>();
		for(PLYFile ply : listFile){
			if(ply.getFile().getName().contains(fileSearch.getText())) accepted.add(ply);
		}
		tableview.setItems(FXCollections.observableArrayList(accepted));
	}
	
	/**
	 * Affiche le message d'erreur
	 * @param title titre du message
	 * @param headerText contenu de l'erreur
	 * @param contentText précision
	 */
	public static void showAlert(String title, String headerText, String contentText){
		Alert alert = new Alert(AlertType.ERROR);

		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		alert.showAndWait().ifPresent(button -> {
			if(button == ButtonType.OK) {
				ShowScene.getViewer().hide();
				ShowScene.getFileChooser().show();
			}
		});
	}
}
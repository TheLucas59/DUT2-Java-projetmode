package com.groupe5.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.groupe5.parser.PLYFile;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class FileSelector {
	@FXML TextField textPath;
	@FXML Text textFileCount;
	private String defaultFileCount = null;
	@FXML ListView<PLYFile> listFiles;
	@FXML TitledPane tpFile;
	private String defaultTpFile = "";
	@FXML Text info_FileFormat;
	private String defaultFileFormat = "";
	@FXML Text info_FilePoints;
	private String defaultFilePoints = "";
	@FXML Text info_FileFaces;
	private String defaultFileFaces = "";
	
	private File selectedFile = null;
	
	public void initialize() {
		defaultFileCount = textFileCount.getText().substring(0, textFileCount.getText().length()-1);
		listFiles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		defaultFileFormat = info_FileFormat.getText()+" ";
		defaultFilePoints = info_FilePoints.getText()+" ";
		defaultFileFaces = info_FileFaces.getText()+" ";
		defaultTpFile = tpFile.getText();
		/*listFiles.getSelectionModel().selectedItemProperty().addListener(( -> {
		     
		});*/
		
		listFiles.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PLYFile>() {
		    @Override
		    public void changed(ObservableValue<? extends PLYFile> observable, PLYFile oldValue, PLYFile newValue) {
		    	PLYFile selectedItem = listFiles.getSelectionModel().getSelectedItem();
			    if(selectedItem == null) {
			    	resetTitledPane();
			    	return;
			    }
		    	selectedFile = selectedItem.getFile();
			    tpFile.setExpanded(true);
			    tpFile.setText(selectedFile.getName());
			    info_FileFormat.setText(defaultFileFormat + "format du fichier");
			    info_FilePoints.setText(defaultFilePoints + "nb");
			    info_FileFaces.setText(defaultFileFaces + "nb faces");
		    }
		});
	}
	
	public void buttonFolder(ActionEvent e){
		DirectoryChooser directoryChooser = new DirectoryChooser();
		if(textPath.getText().length() != 0) directoryChooser.setInitialDirectory(new File(textPath.getText()));
		else if(new File("./exemples/").exists()) directoryChooser.setInitialDirectory(new File("./exemples/"));
		File selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory == null) return;
		//resetTitledPane();
		textPath.setText(selectedDirectory.getAbsolutePath());
		showFiles(filesToList());
	}
	
	
	
	public void openSelectedFile(ActionEvent e){
		// ouvrir le fichier avec le logiciel
	}
	
	private List<File> filesToList(){
		List<File> list = new ArrayList<File>();
		File dir = new File(textPath.getText());
		for(File f : dir.listFiles()) if(f.getName().endsWith(".ply")) list.add(f);
		return list;
	}
	
	private void showFiles(List<File> files) {
		textFileCount.setText(defaultFileCount + files.size());
		this.listFiles.getItems().clear();
		for(File f : files) this.listFiles.getItems().add(new PLYFile(f));
	}
	
	private void resetTitledPane() {
		this.selectedFile = null;
		this.tpFile.setText(defaultTpFile);
		this.info_FileFaces.setText(defaultFileFaces);
		this.info_FileFormat.setText(defaultFileFaces);
		this.info_FilePoints.setText(defaultFilePoints);
		this.tpFile.setExpanded(false);
		System.out.println("reset");
	}
}

package com.groupe5.view;

import java.io.File;

import com.groupe5.parser.PLYFile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class FileSelector {
	@FXML TextField textPath;
	@FXML Text textFileCount;
	@FXML ListView<PLYFile> listFiles;
	@FXML TitledPane tpFile;
	@FXML Text info_FileFormat;
	@FXML Text info_FilePoints;
	@FXML Text info_FileFaces;
	
	private PLYFile selectedFile = null;
	
	public void buttonFolder(ActionEvent e){
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);
	}
	
	public void openSelectedFile(ActionEvent e){
		
	}
}

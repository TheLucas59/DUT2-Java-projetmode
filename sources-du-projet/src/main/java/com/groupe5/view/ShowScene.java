package com.groupe5.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShowScene extends Application{
	private static Stage fileChooser = null;
	private static Stage viewer = null;

	public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        
        File fxmlFile = new File("./src/main/resources/interface/filechooser.fxml");
        
        loader.setLocation(fxmlFile.toURI().toURL());
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Choississez un fichier");
        fileChooser = stage;
        stage.show();
    }
    
    public static void main(String[] args){
        Application.launch(args);
    }
    
    public static Stage getFileChooser() {
    	return fileChooser;
    }
    
    public static Stage getViewer() {
    	if(viewer == null) {
    		FXMLLoader loader = new FXMLLoader();
    		File fxmlFile = new File("./src/main/resources/interface/scene.fxml");
    		try {
				loader.setLocation(fxmlFile.toURI().toURL());
	    		Parent root = loader.load();
	    		Scene scene = new Scene(root, 1280, 720);
	    		viewer = new Stage();
	    		viewer.setScene(scene);
	    		viewer.setTitle("3D Viewer");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return viewer;
    }
}

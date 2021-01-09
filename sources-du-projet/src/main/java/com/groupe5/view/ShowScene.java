package com.groupe5.view;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class qui gère les fenêtres de JavaFX
 * @author pirca
 */
public class ShowScene extends Application{
	private static Stage fileChooser = null;
	private static Stage viewer = null;
	private static Stage viewer2 = null;

	/**
	 * lance le sélecteur de fichier
	 * @param stage stage JavaFX
	 * @throws IOException fichier fxml non trouvé
	 */
	public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/interface/filechooser.fxml"));
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
    
    /**
     * Retourne la fenêtre du sélecteur de fichier
     * @return le stage du FileChooser
     */
    public static Stage getFileChooser() {
    	return fileChooser;
    }
    
    /**
     * Retourne la fenêtre principale du viewer
     * @return le stage Viewer
     */
    public static Stage getViewer() {
    	if(viewer == null) {
    		FXMLLoader loader = new FXMLLoader();
    		try {
	    		loader.setLocation(ShowScene.class.getResource("/interface/scene.fxml"));
    			Parent root = loader.load();
	    		Scene scene = new Scene(root, 1280, 720);
	    		viewer = new Stage();
	    		viewer.setScene(scene);
	    		viewer.setTitle("3D Viewer");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return viewer;
    }

    /**
     * Retourne la fenêtre secondaire du viewer
     * @return le deuxieme stage Viewer
     */
	public static Stage secondWindow() {
		if(viewer2 == null) {
    		FXMLLoader loader = new FXMLLoader();
    		try {
	    		loader.setLocation(ShowScene.class.getResource("/interface/scene2.fxml"));
    			Parent root = loader.load();
	    		Scene scene = new Scene(root, 1280, 720);
	    		viewer2 = new Stage();
	    		viewer2.setScene(scene);
	    		viewer2.setTitle("3D Viewer");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return viewer2;
	}
}

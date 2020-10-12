package com.groupe5.view;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.nashorn.api.tree.ForInLoopTree;

public class ShowScene extends Application{

	public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        
        File fxmlFile = new File("./src/main/resources/interface/scene.fxml");
        
        loader.setLocation(fxmlFile.toURI().toURL());
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("3D Viewer");
        Controller.setStage(stage);
        stage.show();
    }
    
    public static void main(String[] args){
        Application.launch(args);
    }
}

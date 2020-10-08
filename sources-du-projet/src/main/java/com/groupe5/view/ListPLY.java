package com.groupe5.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;

public class ListPLY {

    private ObservableList<String> files;

    public ListPLY(String path) {
        File dir = new File(path);
        String[] list = dir.list();
        
        files = FXCollections.observableArrayList();
        
        for(String s : list)
        	if(s.endsWith(".ply"))
        		files.add(s);
        
    }

    public ObservableList<String> getFiles() {
        return files;
    }

}

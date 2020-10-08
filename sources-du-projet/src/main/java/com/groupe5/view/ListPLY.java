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
        files.addAll(list);
    }

    public ObservableList<String> getFiles() {
        return files;
    }

}

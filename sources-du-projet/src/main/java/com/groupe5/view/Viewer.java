package com.groupe5.view;

import java.io.File;
import java.util.ArrayList;

import com.groupe5.calculation.Matrix;
import com.groupe5.calculation.Translation;
import com.groupe5.geometry.Face;
import com.groupe5.geometry.Modele3D;
import com.groupe5.geometry.Point;
import com.groupe5.observerpattern.Observer;

import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public abstract class Viewer implements Observer {
	
	public static File fileShow;
	
	public abstract Translation getCenter();
	public abstract void clearScreen();
	public abstract Text getZoomText();
	public abstract double getOldZoom();
	public abstract Slider getSlideZoom();

	public Modele3D newModele(Matrix points, ArrayList<Face> faces, Viewer view, File fileShow) {
		if(view instanceof PrimaryView)
			return new Modele3D(points, faces, (PrimaryView) view, fileShow);
		
		return new Modele3D(points, faces, (SecondaryView) view, fileShow);
	}
	
	public Point setObjectCenter(ArrayList<Point> points) {
		double x = 0, y = 0, z = 0;
		int size = points.size();
		
		for(Point p : points) {
			x += p.getX();
			y += p.getY();
			z += p.getZ();
		}
		
		return new Point(((float) x/size), ((float) y/size), ((float) z/size), 0);
	}
	
	public static File getFileToShow() {
		return fileShow;
	}

}

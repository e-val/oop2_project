package view;

import javafx.collections.*;
import javafx.scene.layout.*;
import javafx.scene.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import model.*;
import common.*;

public class DrawingArea extends Pane{

	public DrawingArea(ObservableList<ClassModel> classesToDisplay, Map<ClassModel, ClassView> modelToViewMap, ViewFactory viewFactory){
		getStyleClass().add("drawing-area");

		classesToDisplay.addListener((ListChangeListener)(ListChangeListener.Change change)->{
			while(change.next()){
				if(change.wasAdded()){
					List<ClassModel> added = change.getAddedSubList();
					added.forEach(model->{
						ClassView view = viewFactory.createClassView(model);
						modelToViewMap.put(model, view);
						getChildren().add(view);
					});
				}

				if(change.wasRemoved()){
					List<ClassModel> removed = change.getRemoved();
					removed.forEach(model->{		
						getChildren().remove(modelToViewMap.get(model));
						modelToViewMap.remove(model);
					});
				}
			}
		});
	}

	protected double computePrefWidth(double height){
		return super.computePrefWidth(height) + 20.0;
	}

	protected double computePrefHeight(double width){
		return super.computePrefHeight(width) + 20.0;
	}
}
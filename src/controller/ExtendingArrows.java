package controller;

import javafx.collections.*;
import javafx.beans.value.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import model.*;
import view.*;
import common.*;

public class ExtendingArrows {
	private ObservableList<ClassModel> classesToDisplay;

	private Map<ClassModel, ExtendsArrow> modelToArrowMap = new HashMap<ClassModel, ExtendsArrow>();

	private ViewFactory viewFactory;
	
	private DrawingArea drawingArea;

	private ChangeListener<ClassModel> modelExtendsListener = ((property, oldValue, newValue)->{

		updateAll();
		
	});

	public ExtendingArrows(ObservableList<ClassModel> classesToDisplay, ViewFactory viewFactory, DrawingArea drawingArea){
		this.classesToDisplay = classesToDisplay;
		this.viewFactory = viewFactory;
		this.drawingArea = drawingArea;

		classesToDisplay.addListener((ListChangeListener)(ListChangeListener.Change change)->{
			while(change.next()){
				if(change.wasAdded())
					addListenersToModels(change.getAddedSubList());
				
				if(change.wasRemoved())
					removeListenersFromModels(change.getRemoved());
			}
		});

	}

	private void updateAll(){
		classesToDisplay.forEach((ClassModel model)->{
			if(isArrowDrawnForClass(model))
				removeExtendingArrowForClass(model);

			updateExtendingArrowForClass(model);
		});
	}

	private void updateExtendingArrowForClass(ClassModel model){
		if(parentClassNotNull(model) && parentClassVisible(model)){
			if(!isArrowDrawnForClass(model))
				addExtendingArrowForClass(model);
		}
		else if(modelToArrowMap.containsKey(model)){
			removeExtendingArrowForClass(model);
		}
	}

	private boolean isArrowDrawnForClass(ClassModel model){
		return modelToArrowMap.containsKey(model);
	}

	private boolean parentClassVisible(ClassModel model){
		return classesToDisplay.contains(model.extending.getValue());
	}

	private boolean parentClassNotNull(ClassModel model){
		return model.extending.getValue() != null;
	}

	private void addExtendingArrowForClass(ClassModel model){
		ClassModel parent = model.extending.getValue();
		ExtendsArrow arrow = new ExtendsArrow();

		arrow.bindStart(model.viewModel.attachPointTop.x, model.viewModel.attachPointTop.y);
		arrow.bindEnd(parent.viewModel.attachPointBottom.x, parent.viewModel.attachPointBottom.y);

		modelToArrowMap.put(model, arrow);
		drawingArea.getChildren().add(arrow);
	}

	private void removeExtendingArrowForClass(ClassModel model){
		ExtendsArrow arrow = modelToArrowMap.remove(model);
		drawingArea.getChildren().remove(arrow);
	}

	private void addListenersToModels(List<ClassModel> models){
		models.forEach(model->{
			updateExtendingArrowForClass(model);
			addListenersToModel(model);
		});
		updateAll();
	}

	private void removeListenersFromModels(List<ClassModel> models){
		models.forEach(model->{
			if(isArrowDrawnForClass(model))
				removeExtendingArrowForClass(model);
			
			removeListenersFromModel(model);
		});
		
	}


	private void addListenersToModel(ClassModel model){
		model.extending.addListener(modelExtendsListener);
	}

	private void removeListenersFromModel(ClassModel model){
		model.extending.removeListener(modelExtendsListener);
	}
}
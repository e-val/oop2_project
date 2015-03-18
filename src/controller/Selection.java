package controller;

import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.geometry.Bounds;
import java.util.List;
import javafx.collections.*;

import model.*;
import view.*;

public class Selection {

	private Pane selectionPane = new Pane();

	private double startX, startY;

	private DrawingArea target;

	private ObservableList<Node> selectedNodes = FXCollections.observableArrayList();

	private ObservableList<ClassModel> selectedClasses;
	private ObservableList<ClassModel> classesToDisplay;

	private EventHandler<? super MouseEvent> 
	mousePressedHandler = (e->{
		startX = e.getX();
		startY = e.getY();

		selectionPane.relocate(startX, startY);
		selectionPane.setPrefSize(0, 0);
		target.getChildren().add(selectionPane);
		selectedNodes.clear();
		selectedClasses.clear();
	}), 
	mouseDraggedHandler = (e->{
		double dX = e.getX()-startX;
		double dY = e.getY()-startY;

		if(startX > e.getX()){
			selectionPane.setLayoutX(e.getX());
			dX = startX-e.getX();
		}

		if(startY > e.getY()){
			selectionPane.setLayoutY(e.getY());
			dY = startY-e.getY();
		}

		selectionPane.setPrefSize(dX, dY);

		target.getChildren().forEach(child->{
			if(child != selectionPane){
				Bounds childBounds = child.getBoundsInParent();

				if(selectionPane.getBoundsInParent().intersects(childBounds)){
					if(child instanceof ClassView){
						if(!selectedClasses.contains(((ClassView)child).model)){
							selectedClasses.add(((ClassView)child).model);
							selectedNodes.add(child);
						}
						
					}	
				}
			}
		});
	}), 
	mouseReleasedHandler = (e->{
		target.getChildren().remove(selectionPane);
	});
	

	public Selection(DrawingArea target, ObservableList<ClassModel> selectedClasses, ObservableList<ClassModel> classesToDisplay){
		selectionPane.getStyleClass().add("selection-pane");
		this.target = target;
		this.selectedClasses = selectedClasses;

		target.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
		target.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedHandler);
		target.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedHandler);

		classesToDisplay.addListener((ListChangeListener)(ListChangeListener.Change change)->{
			while(change.next()){
				if(change.wasRemoved()){
					List<ClassModel> removedClasses = change.getRemoved();
					removedClasses.forEach(classModel->{
						selectedNodes.remove(classModel);
					});
				}
			}
		});

		selectedNodes.addListener((ListChangeListener)(ListChangeListener.Change change)->{
			while(change.next()){
				if(change.wasAdded()){
					List<Node> added = change.getAddedSubList();
					added.forEach(node->{
						node.getStyleClass().add("selected");
					});
				}

				if(change.wasRemoved()){
					List<Node> removed = change.getRemoved();
					removed.forEach(node->{
						node.getStyleClass().remove("selected");
					});
				}
			}
			
		});
	}
}
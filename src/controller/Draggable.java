package controller;

import javafx.scene.input.*;
import javafx.scene.*;
import javafx.event.*;

import model.*;
import command.*;


public class Draggable {

	private Node target;
	private ViewModel model;
	private CommandModel commandModel;
	private double startX, startY;

	private MoveViewCommand currentCommand;

	private boolean mouseIsPressed = false;
	private boolean isDragging = false;

	private EventHandler<? super MouseEvent> 
	mousePressedHandler = (e->{
		currentCommand = new MoveViewCommand(model);
		currentCommand.setStartPosition(target.getLayoutX(), target.getLayoutY());
		startX = e.getX();
		startY = e.getY();
		target.toFront();
		mouseIsPressed = true;
		e.consume();
	}), 
	dragDetectedHandler = (e->{
		if(mouseIsPressed){
			mouseIsPressed = false;
			target.getStyleClass().add("dragging");
			isDragging = true;
			e.consume();
		}
	}), 
	mouseDraggedHandler = (e->{
		if(isDragging){
			target.relocate(target.getLayoutX() + e.getX() - startX, target.getLayoutY() + e.getY() - startY);
			e.consume();
		}

	}), 
	mouseReleasedHandler = (e->{
		if(isDragging){
			isDragging = false;
			currentCommand.setNewPosition(target.getLayoutX(), target.getLayoutY());
			target.getStyleClass().remove("dragging");
			if(currentCommand.startX != currentCommand.newX || currentCommand.startY != currentCommand.newY)
				commandModel.store(currentCommand);
			currentCommand = null;

			e.consume();
		}	
	});

	public Draggable(Node target, ViewModel model, CommandModel commandModel){
		this.target = target;
		this.model = model;
		this.commandModel = commandModel;
		addListeners();
	}

	private void addListeners(){
		target.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
		target.addEventHandler(MouseEvent.DRAG_DETECTED, dragDetectedHandler);
		target.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedHandler);
		target.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedHandler);
	}

	private void removeListeners(){
		target.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
		target.removeEventHandler(MouseEvent.DRAG_DETECTED, dragDetectedHandler);
		target.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedHandler);
		target.removeEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedHandler);
	}

	public void destroy(){
		removeListeners();
	}
	
}
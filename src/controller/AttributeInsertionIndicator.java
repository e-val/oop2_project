package controller;

import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.collections.*;
import javafx.scene.control.*;
import java.util.List;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import javafx.beans.property.*;

import view.*;
import command.*;
import model.*;
public class AttributeInsertionIndicator {

	private ClassAttributeListView target;
	private CommandModel commandModel;

	private Pane insertionIndicator = new Pane(){
		{
			getStyleClass().add("insertion-indicator");		
		}
		protected double computePrefHeight(double width){
			return 0.0;	
		}

		protected double computeMinHeight(double width){
			return 1.0;
		}
	};

	private AttributeModificationBar upperModificationBar = new UpperAttributeModificationBar();
	private AttributeModificationBar lowerModificationBar = new LowerAttributeModificationBar();
	private AttributeModificationBar emptyModificationBar = new EmptyAttributeModificationBar();

	private Node hoveredNode = null;
	private boolean isIndicatedUpper = false;
	private boolean isIndicated = false;


	private EventHandler<? super MouseEvent> 
	mouseEnteredHandler = (e->{
		hoveredNode = (Node)e.getSource();
		hoveredNode.getStyleClass().add("attribute-field-hover");
		clearIndicator();
	}),
	mouseMovedHandler = (e->{
		if(e.getY() < hoveredNode.getLayoutBounds().getHeight()/2.0)
			indicateUpperInsertion();
		else
			indicateLowerInstertion();
	}),
	mouseExitedNodeHandler = (e->{

		((Node)e.getSource()).getStyleClass().remove("attribute-field-hover");
	}),
	mouseExitedHandler = (e->{
		clearIndicator();
	}),
	mouseMovedOnEmptyHandler = (e->{
		indicateEmptyInsertion();
	});

	private EventHandler<ActionEvent>
	addButtonClick = (e->{
		AddAttributeToClassCommand command = new AddAttributeToClassCommand(target.attributesList, new ClassAttributeModel(""), target.getChildren().indexOf(insertionIndicator));
		command.redo();
		commandModel.store(command);
	}),
	removeButtonClick = (e->{
		int index = target.getChildren().indexOf(hoveredNode);
		if(e.getSource() == upperModificationBar.removeButton)
			index -= 1;
		
		RemoveAttributeFromClassCommand command = new RemoveAttributeFromClassCommand(target.attributesList, target.attributesList.get(index), index);
		
		command.redo();
		commandModel.store(command);
	});
	
	public AttributeInsertionIndicator(ClassAttributeListView target, CommandModel commandModel){
		this.target = target;
		this.commandModel = commandModel;

		target.setPickOnBounds(false);
		target.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedHandler);
		target.addEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedOnEmptyHandler);

		upperModificationBar.addButton.setOnAction(addButtonClick);
		lowerModificationBar.addButton.setOnAction(addButtonClick);
		emptyModificationBar.addButton.setOnAction(addButtonClick);

		upperModificationBar.removeButton.setOnAction(removeButtonClick);
		lowerModificationBar.removeButton.setOnAction(removeButtonClick);

		addListenersToNodes(target.getChildren());
		target.getChildren().addListener((ListChangeListener)(ListChangeListener.Change change)->{
			while(change.next()){
				if(change.wasAdded())
					addListenersToNodes(change.getAddedSubList());
				
				if(change.wasRemoved())
					removeListenersFromNodes(change.getRemoved());
			}
		});
	}

	private void clearIndicator(){
		target.getChildren().removeAll(upperModificationBar, lowerModificationBar, emptyModificationBar, insertionIndicator);
		isIndicated = false;
	}


	private void indicateEmptyInsertion(){
		if(target.getChildren().isEmpty()){
			
			target.getChildren().add(insertionIndicator);
			target.getChildren().add(emptyModificationBar);
			isIndicated = true;
		}
	}

	private void indicateUpperInsertion(){
		if(!isIndicated || !isIndicatedUpper){
			clearIndicator();
			
			target.getChildren().add(target.getChildren().indexOf(hoveredNode), insertionIndicator);
			target.getChildren().add(target.getChildren().indexOf(hoveredNode)+1, upperModificationBar);
			
			isIndicated = true;
			isIndicatedUpper = true;
		}
	}

	private void indicateLowerInstertion(){
		if(!isIndicated || isIndicatedUpper){
			clearIndicator();
			
			target.getChildren().add(target.getChildren().indexOf(hoveredNode)+1, lowerModificationBar);
			target.getChildren().add(target.getChildren().indexOf(hoveredNode)+1, insertionIndicator);
			
			isIndicated = true;
			isIndicatedUpper = false;
		}
	}

	private void addListenersToNodes(List<Node> nodes){
		nodes.forEach(node->{
			if(node != upperModificationBar && node != lowerModificationBar && node != emptyModificationBar && node != insertionIndicator)
				addListenersToNode(node);
		});
	}

	private void removeListenersFromNodes(List<Node> nodes){
		nodes.forEach(node->{
			if(node != upperModificationBar && node != lowerModificationBar && node != emptyModificationBar && node != insertionIndicator)
				removeListenersFromNode(node);
		});
	}

	private void addListenersToNode(Node node){
		node.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
		node.addEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedHandler);
		node.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedNodeHandler);
	}

	private void removeListenersFromNode(Node node){
		node.removeEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
		node.removeEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedHandler);
		node.removeEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedNodeHandler);
	}

}
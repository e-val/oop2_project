package usertools;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.input.*;

import model.*;
import command.*;
import view.*;

public class AddClassTool implements UserTool {
	
	private BooleanProperty isAvailable = new SimpleBooleanProperty(false);
	private ObservableList<ClassModel> classesToDisplay;
	private CommandModel commandModel;
	private DrawingArea drawingArea;

	private EventHandler<MouseEvent> 
	mousePressOnDrawingAreaListener = ((e)->{
		
		ClassModel newClass = new ClassModel();
		newClass.viewModel.xPosition.setValue(e.getSceneX());
		newClass.viewModel.yPosition.setValue(e.getSceneY());
		
		AddClassCommand addClassCommand = new AddClassCommand(classesToDisplay, newClass);
		addClassCommand.redo();
		commandModel.store(addClassCommand);

		stop();
	});

	public AddClassTool(ObservableList<ClassModel> classesToDisplay, CommandModel commandModel, DrawingArea drawingArea){
		this.classesToDisplay = classesToDisplay;
		this.commandModel = commandModel;
		this.drawingArea = drawingArea;
		isAvailable.setValue(false);
	}

	public void start(){
		drawingArea.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressOnDrawingAreaListener);
	}

	public void stop(){
		drawingArea.removeEventFilter(MouseEvent.MOUSE_PRESSED, mousePressOnDrawingAreaListener);
	}

	public BooleanProperty isUnAvailableProperty(){
		return isAvailable;
	}
}
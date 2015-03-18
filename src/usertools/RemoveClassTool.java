package usertools;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.input.*;

import model.*;
import command.*;
import view.*;

public class RemoveClassTool implements UserTool {
	
	private BooleanProperty isAvailable = new SimpleBooleanProperty();
	private ObservableList<ClassModel> classesToDisplay;
	private ObservableList<ClassModel> selectedClasses;
	private CommandModel commandModel;
	


	public RemoveClassTool(ObservableList<ClassModel> classesToDisplay, ObservableList<ClassModel> selectedClasses, CommandModel commandModel){
		this.selectedClasses = selectedClasses;
		this.classesToDisplay = classesToDisplay;
		this.commandModel = commandModel;

		isAvailable.setValue(selectedClasses.isEmpty());
		selectedClasses.addListener((ListChangeListener)((ListChangeListener.Change change)->{
			isAvailable.setValue(selectedClasses.isEmpty());
		}));
	}

	public void start(){
		MacroCommand command = new MacroCommand();

		selectedClasses.forEach((classModel)->{
			command.add(new RemoveClassCommand(classesToDisplay, classModel));
		});

		command.redo();
		commandModel.store(command);
	}

	public void stop(){
		
	}

	public BooleanProperty isUnAvailableProperty(){
		return isAvailable;
	}
}
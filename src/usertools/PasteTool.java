package usertools;

import javafx.beans.property.*;
import javafx.collections.*;

import model.*;
import command.*;

public class PasteTool implements UserTool {
	
	private BooleanProperty isAvailable = new SimpleBooleanProperty();
	private ObservableList<ClassModel> classesToDisplay;
	private ObservableList<ClassModel> copiedClasses;
	private CommandModel commandModel;

	public PasteTool(ObservableList<ClassModel> classesToDisplay, ObservableList<ClassModel> copiedClasses, CommandModel commandModel){
		this.classesToDisplay = classesToDisplay;
		this.copiedClasses = copiedClasses;
		this.commandModel = commandModel;

		isAvailable.setValue(copiedClasses.isEmpty());
		copiedClasses.addListener((ListChangeListener)((ListChangeListener.Change change)->{
			isAvailable.setValue(copiedClasses.isEmpty());
		}));
	}

	public void start(){
		PasteCommand command = new PasteCommand(copiedClasses, classesToDisplay);
		command.redo();
		commandModel.store(command);
	}

	public void stop(){}

	public BooleanProperty isUnAvailableProperty(){
		return isAvailable;
	}
}
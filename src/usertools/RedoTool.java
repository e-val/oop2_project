package usertools;

import javafx.beans.property.*;
import javafx.collections.*;

import model.*;

public class RedoTool implements UserTool {
	
	private BooleanProperty isAvailable = new SimpleBooleanProperty();
	private CommandModel commandModel;

	public RedoTool(CommandModel commandModel){
		this.commandModel = commandModel;
		isAvailable.setValue(commandModel.redoStack.isEmpty());
		commandModel.redoStack.addListener((ListChangeListener)((ListChangeListener.Change change)->{
			isAvailable.setValue(commandModel.redoStack.isEmpty());
		}));
	}

	public void start(){
		commandModel.redo();
	}

	public void stop(){}

	public BooleanProperty isUnAvailableProperty(){
		return isAvailable;
	}
}
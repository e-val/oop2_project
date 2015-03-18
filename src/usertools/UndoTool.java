package usertools;

import javafx.beans.property.*;
import javafx.collections.*;

import model.*;

public class UndoTool implements UserTool {
	
	private BooleanProperty isAvailable = new SimpleBooleanProperty();
	private CommandModel commandModel;

	public UndoTool(CommandModel commandModel){
		this.commandModel = commandModel;
		isAvailable.setValue(commandModel.undoStack.isEmpty());
		commandModel.undoStack.addListener((ListChangeListener)((ListChangeListener.Change change)->{
			isAvailable.setValue(commandModel.undoStack.isEmpty());
		}));
	}

	public void start(){
		commandModel.undo();
	}

	public void stop(){}

	public BooleanProperty isUnAvailableProperty(){
		return isAvailable;
	}
}
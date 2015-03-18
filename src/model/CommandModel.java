package model;
import javafx.collections.*;
import javafx.beans.property.*;

import java.io.Serializable;

import command.*;
public class CommandModel implements Serializable{
	
	public ObservableList<AppCommand> undoStack = FXCollections.observableArrayList();

	public ObservableList<AppCommand> redoStack = FXCollections.observableArrayList();


	public void store(AppCommand command){
		undoStack.add(command);
		redoStack.clear();
	}

	public void undo(){
		AppCommand command = undoStack.remove(undoStack.size()-1);
		command.undo();
		redoStack.add(command);
	}

	public void redo(){
		AppCommand command = redoStack.remove(redoStack.size()-1);
		command.redo();
		undoStack.add(command);
	}

}
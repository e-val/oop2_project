package command;

import javafx.collections.*;
import model.*;

public class AddClassCommand implements AppCommand {
	
	private ObservableList<ClassModel> classesToDisplay;
	private ClassModel classToAdd;

	public AddClassCommand(ObservableList<ClassModel> classesToDisplay, ClassModel classToAdd){
		this.classesToDisplay = classesToDisplay;
		this.classToAdd = classToAdd;
	}

	public void redo(){
		classesToDisplay.add(classToAdd);
	}

	public void undo(){
		classesToDisplay.remove(classToAdd);
	}
}
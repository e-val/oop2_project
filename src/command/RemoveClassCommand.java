package command;

import javafx.collections.*;
import model.*;

public class RemoveClassCommand implements AppCommand {
	
	private ObservableList<ClassModel> classesToDisplay;
	private ClassModel classToRemove;

	public RemoveClassCommand(ObservableList<ClassModel> classesToDisplay, ClassModel classToRemove){
		this.classesToDisplay = classesToDisplay;
		this.classToRemove = classToRemove;
	}

	public void redo(){
		classesToDisplay.remove(classToRemove);	
	}

	public void undo(){
		classesToDisplay.add(classToRemove);
	}
}
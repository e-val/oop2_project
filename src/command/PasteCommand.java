package command;

import javafx.collections.*;
import javafx.beans.property.*;

import java.util.List;
import java.util.ArrayList;

import model.*;

public class PasteCommand implements AppCommand {
	
	protected List<ClassModel> classesToPaste = new ArrayList<ClassModel>();
	private ObservableList<ClassModel> classesToDisplay;

	public PasteCommand(List<ClassModel> classesToPaste, ObservableList<ClassModel> classesToDisplay){
		classesToPaste.forEach((classModel)->{
			try{
				this.classesToPaste.add(classModel.clone());
			}
			catch(Exception e){
				
			}
		});
		this.classesToDisplay = classesToDisplay;
	}

	public void redo(){
		classesToDisplay.addAll(classesToPaste);
	}

	public void undo(){
		classesToDisplay.removeAll(classesToPaste);
	}
}
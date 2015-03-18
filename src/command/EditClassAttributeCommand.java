package command;

import model.*;

public class EditClassAttributeCommand implements AppCommand {
	
	ClassAttributeModel classAttributeModel;
	String oldValue, newValue;

	public EditClassAttributeCommand(ClassAttributeModel classAttributeModel, String oldValue, String newValue){
		this.classAttributeModel = classAttributeModel;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public void redo(){
		classAttributeModel.setPropertiesFromString(newValue);
	}

	public void undo(){
		classAttributeModel.setPropertiesFromString(oldValue);
	}
}
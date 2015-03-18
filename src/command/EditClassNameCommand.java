package command;

import model.*;

public class EditClassNameCommand implements AppCommand {
	
	TypeModel classModel;
	String oldValue, newValue;

	public EditClassNameCommand(TypeModel classModel, String oldValue, String newValue){
		this.classModel = classModel;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public void redo(){
		classModel.typeName.setValue(newValue);
	}

	public void undo(){
		classModel.typeName.setValue(oldValue);
	}
}
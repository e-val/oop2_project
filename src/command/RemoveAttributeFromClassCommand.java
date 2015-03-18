package command;

import javafx.collections.*;
import javafx.beans.property.*;

import model.*;

public class RemoveAttributeFromClassCommand extends AddAttributeToClassCommand {
	
	public RemoveAttributeFromClassCommand(ObservableList<ClassAttributeModel> attributeList, ClassAttributeModel attribute, int index){
		super(attributeList, attribute, index);
	}

	public void redo(){
		attributeList.remove(attribute);
	}

	public void undo(){
		attributeList.add(index, attribute);
	}
}
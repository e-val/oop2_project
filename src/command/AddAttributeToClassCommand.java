package command;

import javafx.collections.*;
import javafx.beans.property.*;

import model.*;

public class AddAttributeToClassCommand implements AppCommand {
	
	protected ObservableList<ClassAttributeModel> attributeList;
	protected ClassAttributeModel attribute;
	protected int index;

	public AddAttributeToClassCommand(ObservableList<ClassAttributeModel> attributeList, ClassAttributeModel attribute, int index){
		this.attributeList = attributeList;
		this.attribute = attribute;
		this.index = index;
	}

	public void redo(){
		attributeList.add(index, attribute);
	}

	public void undo(){
		attributeList.remove(attribute);
	}
}
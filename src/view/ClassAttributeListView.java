package view;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;

import model.*;
import command.*;

public class ClassAttributeListView extends VBox {
	
	public final ObservableList<ClassAttributeModel> attributesList;
	private CommandModel commandModel;

	public ClassAttributeListView(ObservableList<ClassAttributeModel> attributesList, CommandModel commandModel){
		getStyleClass().add("attribute-list-view");
		this.attributesList = attributesList;
		this.commandModel = commandModel;

		updateChildren();
		attributesList.addListener((ListChangeListener)(ListChangeListener.Change change)->{
			updateChildren();
		});
	}

	private void updateChildren(){
		getChildren().forEach(node->{

		});
		getChildren().clear();
		attributesList.forEach((ClassAttributeModel attr)->{
			EditableTextView view = new EditableTextView(attr.stringRepresentation.getValue());

			view.inValue.bind(attr.stringRepresentation);
			view.outValue.addListener((object, oldValue, newValue)->{
				String old = view.inValue.getValue();
				attr.setPropertiesFromString(newValue);
				if(old != attr.stringRepresentation.getValue()){
					EditClassAttributeCommand command = new EditClassAttributeCommand(attr, old, attr.stringRepresentation.getValue());
					commandModel.store(command);
				}
				
			});
			getChildren().add(view);
		});
	}

	protected double computeMinHeight(double width){
		return 10.0;
	}
}
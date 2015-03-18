package view;
import model.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.ListChangeListener;
import javafx.scene.input.*;

import controller.*;

public class ClassView extends VBox {
	public final ClassModel model;
	public ClassView(ClassModel model, EditableTextView nameView, ClassAttributeListView methodListView, ClassAttributeListView attributeListView){
		this.model = model;
		this.getStyleClass().add("class-view");

		this.getChildren().add(nameView);
		this.getChildren().add(new Separator());
		this.getChildren().add(attributeListView);
		this.getChildren().add(new Separator());
		this.getChildren().add(methodListView);
	}

}
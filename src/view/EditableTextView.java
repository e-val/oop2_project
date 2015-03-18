package view;

import javafx.scene.control.*;
import javafx.beans.property.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.event.*;

public class EditableTextView extends BorderPane {
	
	private TextField editableField = new TextField();
	private Label uneditableField = new Label();

	private boolean makeEditable = false;

	public final StringProperty outValue;
	public final StringProperty inValue;

	public EditableTextView(String defaultText){
		
		outValue = new SimpleStringProperty(defaultText);
		inValue = new SimpleStringProperty(defaultText);
		editableField.textProperty().setValue(inValue.getValue());
		uneditableField.textProperty().bind(inValue);

		editableField.prefWidthProperty().bind(widthProperty());
		editableField.prefHeightProperty().bind(heightProperty());

		BorderPane.setAlignment(uneditableField, Pos.TOP_LEFT);
		BorderPane.setAlignment(editableField, Pos.TOP_LEFT);

		getStyleClass().add("editable-text-view");
		

		lock();


		inValue.addListener((object, oldValue, newValue)->{
			editableField.textProperty().setValue(newValue);
		});
		
		editableField.focusedProperty().addListener((object, oldValue, newValue)->{
			if(!newValue){
				lock();
				outValue.setValue(editableField.textProperty().getValue());
			}
		});

		setOnMousePressed(e->{
			makeEditable = true;
		});

		setOnDragDetected(e->{
			makeEditable = false;
		});

		setOnMouseClicked((event)->{
			if(makeEditable)
				unlock();
		});

		editableField.setOnAction((event)->{
			lock();
		});
	}

	private void unlock(){
		setCenter(editableField);
		
		editableField.requestFocus();
		editableField.end();
	}

	private void lock(){
		setCenter(uneditableField);
	}
	
}
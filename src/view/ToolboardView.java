package view;
import javafx.scene.control.*;

import model.*;

import javafx.geometry.Orientation;

public class ToolboardView extends ToolBar {
	public Button 
	undoButton = new Button("Undo"),
	redoButton = new Button("Redo");

	public Button 
	copyButton = new Button("Copy"),
	pasteButton = new Button("Paste"),
	removeClassButton = new Button("Delete");

	public Button 
	classButton = new Button("Class");

	public Button 
	newButton = new Button("New"),
	saveButton = new Button("Save"),
	loadButton = new Button("Load");

	public Button 
	extendingButton = new Button("Extends");

	public Button 
	loadSourceButton = new Button("Load Source"),
	generateSourceButton = new Button("Generate Source");

	public ToolboardView(){
		getItems().addAll(
			classButton,
			new Separator(Orientation.VERTICAL),
			extendingButton,
			new Separator(Orientation.VERTICAL),
			undoButton,
			redoButton,
			new Separator(Orientation.VERTICAL),
			copyButton,
			pasteButton,
			removeClassButton,
			new Separator(Orientation.VERTICAL),
			newButton,
			saveButton,
			loadButton,
			new Separator(Orientation.VERTICAL),
			loadSourceButton,
			generateSourceButton
		);

		getStyleClass().add("toolbar");
	}

	private void initUndoRedo(){

	}
	
}
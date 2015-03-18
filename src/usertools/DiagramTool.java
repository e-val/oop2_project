package usertools;

import javafx.beans.property.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import model.*;

public class DiagramTool implements UserTool {
	
	protected BooleanProperty isUnAvailable = new SimpleBooleanProperty(false);

	protected AppModel appModel;
	protected FileChooser fileChooser;

	public DiagramTool(AppModel appModel){
		this.appModel = appModel;
		
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("YoML", "*.yoml"));

	}

	protected void clearCurrentDiagram(){
		appModel.commandModel.undoStack.clear();
		appModel.commandModel.redoStack.clear();

		appModel.classesToDisplay.clear();
	}

	public void start(){

	}

	public void stop(){

	}

	public BooleanProperty isUnAvailableProperty(){
		return isUnAvailable;
	}


}
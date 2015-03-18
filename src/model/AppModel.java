package model;

import javafx.stage.Stage;
import javafx.collections.*;
import javafx.beans.property.*;

import java.util.Map;
import java.util.HashMap;

import view.*;

public class AppModel {

	public Stage stage;
	
	public CommandModel commandModel = new CommandModel();

	public ObservableList<ClassModel> classesToDisplay = FXCollections.observableArrayList();

	public ObservableList<ClassModel> selectedClasses = FXCollections.observableArrayList();

	public ObservableList<ClassModel> copiedClasses = FXCollections.observableArrayList();

	public Map<ClassModel, ClassView> modelToViewMap = new HashMap<ClassModel, ClassView>();

}
package usertools;

import javafx.beans.property.*;
import javafx.collections.*;

import model.*;

public class CopyTool implements UserTool {
	
	private BooleanProperty isAvailable = new SimpleBooleanProperty();
	private ObservableList<ClassModel> selectedClasses;
	private ObservableList<ClassModel> copiedClasses;

	public CopyTool(ObservableList<ClassModel> selectedClasses, ObservableList<ClassModel> copiedClasses){
		this.selectedClasses = selectedClasses;
		this.copiedClasses = copiedClasses;

		isAvailable.setValue(selectedClasses.isEmpty());
		selectedClasses.addListener((ListChangeListener)((ListChangeListener.Change change)->{
			isAvailable.setValue(selectedClasses.isEmpty());
		}));
	}

	public void start(){
		copiedClasses.clear();
		selectedClasses.forEach((classModel)->{
			try{
				copiedClasses.add(classModel.clone());
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		});
	}

	public void stop(){}

	public BooleanProperty isUnAvailableProperty(){
		return isAvailable;
	}
}
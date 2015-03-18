package usertools;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.input.*;
import javafx.event.*;

import java.util.Map;
import java.util.LinkedHashMap;

import model.*;
import view.*;
import command.*;

public class ClassExtendsTool implements UserTool {
	
	private BooleanProperty isUnAvailable = new SimpleBooleanProperty(false);

	private CommandModel commandModel;

	private DrawingArea drawingArea;

	private Map<ClassModel, ClassView> modelToViewMap;

	private Map<ClassView, EventHandler<MouseEvent>> viewToListenerMap = new LinkedHashMap<ClassView, EventHandler<MouseEvent>>();

	private EventHandler<MouseEvent> 
	mousePressOnDrawingAreaListener = ((e)->{
		stop();
		e.consume();
	});

	private ClassModel first = null;
	private ClassModel oldExtends = null;
	private ClassModel newExtends = null;

	public ClassExtendsTool(CommandModel commandModel, Map<ClassModel, ClassView> modelToViewMap, DrawingArea drawingArea){
		this.commandModel = commandModel;
		this.modelToViewMap = modelToViewMap;
		this.drawingArea = drawingArea;
	}

	public void start(){
		first = null;
		oldExtends = null;
		newExtends = null;
		addListeners();
	}

	public void stop(){	
		removeListeners();
		if(first != null){
			SetClassExtendsCommand command = new SetClassExtendsCommand(first, oldExtends, newExtends);
			command.redo();
			commandModel.store(command);
		}
		
		first = null;
		oldExtends = null;
		newExtends = null;

	}

	public BooleanProperty isUnAvailableProperty(){
		return isUnAvailable;
	}

	private void addListeners(){
		modelToViewMap.keySet().forEach(classModel->{
			ClassView classView = modelToViewMap.get(classModel);
			EventHandler<MouseEvent> mousePressOnClassListener = ((e)->{
				if(first == null){
					first = classModel;
					oldExtends = classModel.extending.getValue();
					classModel.extending.setValue(null);
				}
				else{
					newExtends = classModel;
					stop();
				}
				
				e.consume();
			});

			viewToListenerMap.put(classView, mousePressOnClassListener);
			classView.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressOnClassListener);
		});
		drawingArea.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressOnDrawingAreaListener);
	}

	private void removeListeners(){
		modelToViewMap.values().forEach(classView->{
			if(viewToListenerMap.containsKey(classView))
				classView.removeEventFilter(MouseEvent.MOUSE_PRESSED, viewToListenerMap.get(classView));

			viewToListenerMap.remove(classView);
		});
		drawingArea.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressOnDrawingAreaListener);
	}

}
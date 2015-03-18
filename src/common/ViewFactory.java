package common;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.*;

import java.util.Map;
import java.util.LinkedHashMap;

import model.*;
import view.*;
import controller.*;
import command.*;
import usertools.*;

public class ViewFactory {

	private AppModel appModel;

	public ViewFactory(AppModel appModel){
		this.appModel = appModel;
	}

	public ClassView createClassView(ClassModel model){
		ClassAttributeListView methodListView = createClassAttributeListView(model.methods);
		ClassAttributeListView attributeListView = createClassAttributeListView(model.attributes);

		EditableTextView nameView = new EditableTextView(model.typeName.getValue());
		nameView.inValue.bind(model.typeName);
		nameView.outValue.addListener((object, oldValue, newValue)->{
			if(oldValue != newValue){
				EditClassNameCommand command = new EditClassNameCommand(model, oldValue, newValue);
				command.redo();
				appModel.commandModel.store(command);
			}
		});

		ClassView view = new ClassView(model, nameView, methodListView, attributeListView);

		view.layoutXProperty().bindBidirectional(model.viewModel.xPosition);
		view.layoutYProperty().bindBidirectional(model.viewModel.yPosition);

		model.viewModel.width.bind(view.widthProperty());
		model.viewModel.height.bind(view.heightProperty());

		new Draggable(view, model.viewModel, appModel.commandModel);

		return view;
	}

	public ClassAttributeListView createClassAttributeListView(ObservableList<ClassAttributeModel> attributesList){
		ClassAttributeListView attributesView = new ClassAttributeListView(attributesList, appModel.commandModel);
		
		new AttributeInsertionIndicator(attributesView, appModel.commandModel);

		return attributesView;
	}

	public ScrollPane createScrollPane(){
		ScrollPane scrollPane = new ScrollPane();

		AnchorPane.setTopAnchor(scrollPane, 0.0);
		AnchorPane.setRightAnchor(scrollPane, 0.0);
		AnchorPane.setBottomAnchor(scrollPane, 0.0);
		AnchorPane.setLeftAnchor(scrollPane, 0.0);

		return scrollPane;
	}

	public DrawingArea createDrawingArea(ScrollPane scrollPane){
		DrawingArea drawingArea = new DrawingArea(appModel.classesToDisplay, appModel.modelToViewMap, this);

		drawingArea.minWidthProperty().bind(scrollPane.widthProperty().subtract(2));
		drawingArea.minHeightProperty().bind(scrollPane.heightProperty().subtract(2));

		new Selection(drawingArea, appModel.selectedClasses, appModel.classesToDisplay);
		new ExtendingArrows(appModel.classesToDisplay, this, drawingArea);
		
		return drawingArea;
	}

	public AnchorPane createAnchorPane(){
		AnchorPane anchorPane = new AnchorPane();

		return anchorPane;
	}

	public ToolboardView createToolboardView(AnchorPane anchorPane, DrawingArea drawingArea, Stage stage){
		ToolboardView toolbar = new ToolboardView();

		AnchorPane.setBottomAnchor(toolbar, 30.0);

		anchorPane.widthProperty().addListener((property, oldValue, newValue)->{
			AnchorPane.setLeftAnchor(toolbar, newValue.doubleValue()/2 - toolbar.widthProperty().getValue()/2);
		});

		Map<Button, UserTool> buttonToolMap = new LinkedHashMap<Button, UserTool>();
		buttonToolMap.put(toolbar.undoButton, new UndoTool(appModel.commandModel));		
		buttonToolMap.put(toolbar.redoButton, new RedoTool(appModel.commandModel));		
		buttonToolMap.put(toolbar.copyButton, new CopyTool(appModel.selectedClasses, appModel.copiedClasses));	
		buttonToolMap.put(toolbar.pasteButton, new PasteTool(appModel.classesToDisplay, appModel.copiedClasses, appModel.commandModel));	
		buttonToolMap.put(toolbar.classButton, new AddClassTool(appModel.classesToDisplay, appModel.commandModel, drawingArea));	
		buttonToolMap.put(toolbar.removeClassButton, new RemoveClassTool(appModel.classesToDisplay, appModel.selectedClasses, appModel.commandModel));

		buttonToolMap.put(toolbar.newButton, new NewDiagramTool(appModel));	
		buttonToolMap.put(toolbar.saveButton, new SaveDiagramTool(appModel));	
		buttonToolMap.put(toolbar.loadButton, new LoadDiagramTool(appModel));	

		buttonToolMap.put(toolbar.loadSourceButton, new LoadFromSourceTool(appModel));	

		buttonToolMap.put(toolbar.extendingButton, new ClassExtendsTool(appModel.commandModel, appModel.modelToViewMap, drawingArea));	

		new ToolboardController(toolbar, buttonToolMap);

		return toolbar;
	}

}
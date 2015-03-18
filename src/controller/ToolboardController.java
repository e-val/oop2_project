package controller;

import javafx.scene.control.*;
import javafx.event.*;

import java.util.Map;
import java.util.LinkedHashMap;

import usertools.*;
import view.*;

public class ToolboardController {

	private UserTool currentTool;

	private Map<Button, UserTool> buttonToolMap;

	private EventHandler<ActionEvent> 
	onButtonClick = ((e)->{
		if(currentTool != null)
			currentTool.stop();

		currentTool = buttonToolMap.get(e.getSource());
		currentTool.start();
	});

	public ToolboardController(ToolboardView toolbar, Map<Button, UserTool> buttonToolMap){
		this.buttonToolMap = buttonToolMap;

		buttonToolMap.keySet().forEach(button->{
			button.disableProperty().bind(buttonToolMap.get(button).isUnAvailableProperty());
			button.setOnAction(onButtonClick);
		});
	}
}
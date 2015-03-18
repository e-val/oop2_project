package usertools;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;

import model.*;

public class NewDiagramTool extends DiagramTool{
	
	public NewDiagramTool(AppModel appModel){
		super(appModel);
	}


	public void start(){
		clearCurrentDiagram();
	}
}
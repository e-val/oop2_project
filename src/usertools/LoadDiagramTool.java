package usertools;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

import java.util.ArrayList;

import model.*;

public class LoadDiagramTool extends DiagramTool{
	
	public LoadDiagramTool(AppModel appModel){
		super(appModel);
	}


	public void start(){
		fileChooser.setTitle("Load diagram");

		File loadFile = fileChooser.showOpenDialog(appModel.stage);
		if(loadFile != null || !loadFile.exists()){
			clearCurrentDiagram();
			try{
				ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(loadFile));
				
				appModel.classesToDisplay.addAll((ArrayList)inStream.readObject());
				inStream.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
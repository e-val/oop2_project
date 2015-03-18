package usertools;
import javafx.application.*;
import javafx.stage.DirectoryChooser;

import java.io.File;

import model.*;
import common.*;


public class LoadFromSourceTool extends DiagramTool {
	
	private DirectoryChooser directoryChoser = new DirectoryChooser();

	public LoadFromSourceTool(AppModel appModel){
		super(appModel);
		directoryChoser.setTitle("Select source root directory");
	}

	public void start(){
		File rootDirectory = directoryChoser.showDialog(appModel.stage);
		if(rootDirectory != null || !rootDirectory.exists()){
			ProjectLoader projectLoader = new ProjectLoader(rootDirectory.getAbsolutePath());
			projectLoader.startLoadingOnNewThread(()->{
				Platform.runLater(()->{
					appModel.classesToDisplay.clear();
					appModel.classesToDisplay.addAll(projectLoader.getClassModels());
				});
				
			});
		}
	}

}
package usertools;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;

import model.*;

public class SaveDiagramTool extends DiagramTool{
	
	public SaveDiagramTool(AppModel appModel){
		super(appModel);
	}


	public void start(){
		fileChooser.setTitle("Save diagram");

		File saveFile = fileChooser.showSaveDialog(appModel.stage);
		if(saveFile != null){

			if(!saveFile.getAbsolutePath().endsWith(".yoml"))
				saveFile = new File(saveFile.getAbsolutePath()+".yoml");
			
			try{
				if(saveFile.exists())
					saveFile.delete();

				saveFile.createNewFile();
				ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(saveFile));

				outStream.writeObject(new ArrayList(appModel.classesToDisplay));
				outStream.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

}
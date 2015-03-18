package exec;
import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.beans.binding.*;
import javafx.beans.property.*;
import javafx.scene.image.*;

import common.*;
import view.*;
import model.*;
import controller.*;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		AppModel appModel = new AppModel();

		appModel.stage = stage;
		ViewFactory viewFactory = new ViewFactory(appModel);

		ScrollPane scrollPane = viewFactory.createScrollPane();
		DrawingArea drawingArea = viewFactory.createDrawingArea(scrollPane);
		AnchorPane anchorPane = viewFactory.createAnchorPane();
		ToolboardView toolbar = viewFactory.createToolboardView(anchorPane, drawingArea, stage);


		scrollPane.setContent(drawingArea);
		anchorPane.getChildren().add(scrollPane);
		anchorPane.getChildren().add(toolbar);

		Scene scene = new Scene(anchorPane, 1000, 600);
		scene.getStylesheets().add(Constants.STYLESHEET_PATH);
		stage.setScene(scene);

		stage.setTitle("YouML");
		stage.show();
		AnchorPane.setLeftAnchor(toolbar, anchorPane.widthProperty().doubleValue()/2 - toolbar.widthProperty().getValue()/2); 
	}

	public static void main(String[] args) {
		launch(args);
	}
}
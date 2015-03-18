package view;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;

import javafx.scene.text.Font;

public class AttributeModificationBar extends AnchorPane {
	private ImageView plusView = new ImageView(new Image("img/plus-16.png"));
	private ImageView minusView = new ImageView(new Image("img/minus-16.png"));

	public final Button addButton = new Button("", plusView);
	public final Button removeButton = new Button("", minusView);

	protected VBox modificationBar = new VBox();

	public AttributeModificationBar(){
		AnchorPane.setRightAnchor(modificationBar, -9.0);
		getChildren().add(modificationBar);
		addButton.getStyleClass().add("plus");
		removeButton.getStyleClass().add("minus");
		setPickOnBounds(false);

		addButton.setFont(new Font(0));
		removeButton.setFont(new Font(0));

		plusView.fitHeightProperty().setValue(10.0);
		plusView.fitWidthProperty().setValue(10.0);

		minusView.fitHeightProperty().setValue(10.0);
		minusView.fitWidthProperty().setValue(10.0);
	}

	protected double computePrefHeight(double width){
		return 0.0;	
	}

	protected double computeMinHeight(double width){
		return 1.0;
	}

	protected double computeMaxHeight(double width){
		return 0.0;
	}

	public void setIndex(int index){

	}
}
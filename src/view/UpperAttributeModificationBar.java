package view;

import javafx.scene.layout.*;

public class UpperAttributeModificationBar extends AttributeModificationBar {
	
	public UpperAttributeModificationBar(){
		modificationBar.getChildren().addAll(addButton, removeButton);
		AnchorPane.setTopAnchor(modificationBar, -35.0);
	}

}
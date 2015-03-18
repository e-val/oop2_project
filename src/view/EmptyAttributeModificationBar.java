package view;

import javafx.scene.layout.*;

public class EmptyAttributeModificationBar extends AttributeModificationBar {
	
	public EmptyAttributeModificationBar(){
		modificationBar.getChildren().add(addButton);
		AnchorPane.setTopAnchor(modificationBar, -5.0);
	}

}
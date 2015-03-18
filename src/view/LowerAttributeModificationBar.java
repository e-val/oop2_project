package view;

import javafx.scene.layout.*;

public class LowerAttributeModificationBar extends AttributeModificationBar {
	
	public LowerAttributeModificationBar(){
		modificationBar.getChildren().addAll(removeButton, addButton);
		AnchorPane.setTopAnchor(modificationBar, -21.0);
	}

}
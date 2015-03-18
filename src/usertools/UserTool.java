package usertools;

import javafx.beans.property.*;

public interface UserTool {
	
	public void start();

	public void stop();

	public BooleanProperty isUnAvailableProperty();
}
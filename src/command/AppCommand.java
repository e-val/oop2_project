package command;

import java.io.Serializable;

public interface AppCommand extends Serializable{
	
	public void redo();

	public void undo();
}
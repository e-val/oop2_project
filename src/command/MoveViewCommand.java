package command;

import model.*;

public class MoveViewCommand implements AppCommand {
	
	public  double startX, startY;

	public  double newX, newY;

	private ViewModel model;

	public MoveViewCommand(ViewModel model){
		this.model = model;
	}

	public void setStartPosition(double x, double y){
		startX = x;
		startY = y;
	}

	public void setNewPosition(double x, double y){
		newX = x;
		newY = y;
	}

	public void redo(){
		model.xPosition.setValue(newX);
		model.yPosition.setValue(newY);
	}

	public void undo(){
		model.xPosition.setValue(startX);
		model.yPosition.setValue(startY);
	}
	
	public String toString(){
		return "MoveViewCommand model="+model+", startPosition=("+startX+","+startY+"), newPosition=("+newX+","+ newY+")";
	}
}
package command;

import model.*;

public class SetClassExtendsCommand implements AppCommand {
	
	private ClassModel target;

	private ClassModel oldExtends;

	private ClassModel newExtends;

	public SetClassExtendsCommand(ClassModel target, ClassModel oldExtends, ClassModel newExtends){
		this.target = target;
		this.oldExtends = oldExtends;
		this.newExtends = newExtends;
	}

	public void redo(){
		target.extending.setValue(newExtends);
	}

	public void undo(){
		target.extending.setValue(oldExtends);
	}

}
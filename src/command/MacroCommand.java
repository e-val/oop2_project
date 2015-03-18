package command;

import java.util.List;
import java.util.ArrayList;

public class MacroCommand implements AppCommand {
	
	private List<AppCommand> commands = new ArrayList<AppCommand>();

	public void redo(){
		commands.forEach((command)->{
			command.redo();
		});
	}

	public void undo(){
		for(int i = commands.size()-1; i >= 0; i--)
			commands.get(i).undo();
	}

	public void add(AppCommand command){
		commands.add(command);
	}

}
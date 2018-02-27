package witpvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.isOp()) {
			String cmdName = cmd.getName();
			
			if (cmdName.equalsIgnoreCase("test")) {
				sender.sendMessage("yes this is a test");
				return true;
				
			} else if (cmdName.equalsIgnoreCase("match")) {
				if (args.length == 0) {
					
					return false;
					
				} else if (args.length == 1) {
					if (args[1].equalsIgnoreCase("create")) {
						
					}
				}
			}
			
			return false;
		}
		
		return false;
	}

}


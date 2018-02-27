package witpvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.isOp()) {
			if (cmd.getName().equalsIgnoreCase("test")) {
				sender.sendMessage("yes this is a test");
				return true;
			}
			return false;
		}
		return false;
	}

}


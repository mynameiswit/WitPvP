package witpvp;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import witpvp.match.Match;

public class CommandListener implements CommandExecutor {
	
	private Map<CommandSender, Match> hooks = new HashMap<CommandSender, Match>();
	
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
					if (args[0].equalsIgnoreCase("create")) {
						Match match = new Match();
						int id = match.getID();
						
						sender.sendMessage("Match ID " + id + " created.");
						
						if (sender instanceof Player) {
							Player player = ((Player) sender);
							match.addPlayer(player);
						
							// Hook the player to the match.
							((Player) sender).performCommand("hook " + match.getID());
						}
						return true;
						
					}
				}
			} else if (cmdName.equalsIgnoreCase("hook")) {
				if (args.length == 1) {
					if (StringUtils.isNumeric(args[0])) {
						for (Match activeMatch : Wp.getMatches()) {
							if (activeMatch.getID() == Integer.parseInt(args[0])) {
								int id = Integer.parseInt(args[0]);
								if (hook(sender, id)) {
									sender.sendMessage(ChatColor.GREEN + "You are now hooked onto match " + id + ".");
									
								}
								return true;
								
							}
						}
					} else if (args[1].equalsIgnoreCase("release")) {
						((Player) sender).performCommand("release");
						
					} else {
						return false;
						
					}
				} else {
					return false;
					
				}
			} else if (cmdName.equalsIgnoreCase("release")) {
				if (args.length == 0) {
					if (isHooked(sender)) {
						release(sender);
						sender.sendMessage(ChatColor.GREEN + "Hook released.");
					} else {
						sender.sendMessage(ChatColor.GREEN + "You are not currently hooked.");
					}
					return true;
					
				} else {
					return false;
					
				}
			}
			
			return false;
		}
		
		return false;
	}
	
	private Match getHook(CommandSender cmdSender) {
		return hooks.get(cmdSender);
	}

	private boolean hook(CommandSender cmdSender, int id) {
		Match match = Match.getMatch(id);
		if (match != null) {
			hooks.put(cmdSender, match);
			return true;
		}
		return false;
	}

	private boolean isHooked(CommandSender cmdSender) {
		if (hooks.containsKey(cmdSender)) {
			return true;
		}
		return false;
	}

	private void release(CommandSender cmdSender) {
		hooks.remove(cmdSender);
	}

}


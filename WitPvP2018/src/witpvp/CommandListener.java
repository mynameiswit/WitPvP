package witpvp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import witpvp.match.Match;
import witpvp.match.Round;

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
						
					} else if (args[0].equalsIgnoreCase("end")) {
						if (isHooked(sender)) {
							Match match = getHook(sender);
							match.end();
							
							sender.sendMessage("Ended match " + match.getID() + ".");
							
							return true;
							
						} else {
							return false;
							
						}
						
					} else if (args[0].equalsIgnoreCase("status")) {
						if (isHooked(sender)) {
							Match match = getHook(sender);
							sender.sendMessage("Match " + match.getID() + " - " + match.getStatus().toString());
							
							return true;
							
						} else {							
							sender.sendMessage("Statuses of all matches:");
							
							for (Match m : Wp.getMatches()) {
								sender.sendMessage(ChatColor.GRAY + "Match " + m.getID() + " - " + m.getStatus().toString());
							}	
							
							return true;
							
						}
					}
				}
			} else if (cmdName.equalsIgnoreCase("round")) {
				if (args.length == 0) {
					return false;
					
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("create")) {
						if (isHooked(sender)) {
							Match match = getHook(sender);
							match.newRound();
							
							sender.sendMessage("Created a new round in match " + match.getID() + ".");
							
							return true;
							
						} else {
							return false;
							
						}
						
					} else if (args[0].equalsIgnoreCase("end")) {
						if (isHooked(sender)) {
							Match match = getHook(sender);
							
							if (match.hasActiveRound()) {
								Round round = match.getActiveRound();
								round.end();
								
								sender.sendMessage("Ended active round in match " + match.getID() + ".");
								
							} else {
								sender.sendMessage("Match " + match.getID() + " does not currently have any active rounds.");
							
							}

							return true;
							
						} else {
							return false;
							
						}
						
					} else if (args[0].equalsIgnoreCase("start")) {
						if (isHooked(sender)) {
							Match match = getHook(sender);
							
							if (match.hasActiveRound()) {
								sender.sendMessage("Match " + match.getID() + " already has an active round.");
								
							} else {
								Round round = match.getLatestRound();
								round.start();
								
								sender.sendMessage("Started last created match in match " + match.getID() + ".");
							}

							return true;
							
						} else {
							return false;
							
						}
						
					} else if (args[0].equalsIgnoreCase("status")) {
						if (isHooked(sender)) {
							Match match = getHook(sender);
							
							sender.sendMessage("Statuses of all rounds in match " + match.getID() + ":");
							
							List<Round> rounds = match.getRounds();
							
							for (Round r : match.getRounds()) {
								sender.sendMessage(ChatColor.GRAY + "Round " + Integer.toString(rounds.indexOf(r)+1) + " - " + r.getStatus().toString());
							}	

							return true;
							
						} else {
							return false;
							
						}
					}
				}
				
			} else if (cmdName.equalsIgnoreCase("charge")) {
				if (sender instanceof Player) {
					if (args.length == 1) {
						PlayerProfile profile = Wp.getPlayerProfile(((Player) sender));
						
						try {
							double charge = Double.parseDouble(args[0]);
							profile.getBattleProfile().setCharge(charge);
							
							((Player) sender).sendMessage("Setting " + ((Player) sender).getName() + "'s charge to " + args[0] + ".");
						} catch (NumberFormatException e) {
							return false;
						}
						
						return true;
						
					} else {
						return false;
						
					}
				}
				
			} else if (cmdName.equalsIgnoreCase("world")) {
				if (sender instanceof Player) {
					if (args.length == 1) {
						if (args[0].equalsIgnoreCase("list")) {
							((Player) sender).sendMessage("Loaded world files:");

							for (World world : Bukkit.getWorlds()) {
								((Player) sender).sendMessage(ChatColor.GRAY + world.getName());
							}
							
							((Player) sender).sendMessage("List end.");

							
							return true;
							
						}
						return false;
					} else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("teleport")) {
							World world = Bukkit.getWorld(args[1]);
							
							if (world != null) {
								((Player) sender).teleport(world.getSpawnLocation());
								Wp.broadcast(((Player) sender).getName() + " teleported to world " + ChatColor.GREEN + args[1] + ".");
							}
							
							return true;
							
						} else if (args[0].equalsIgnoreCase("load")) {
							Bukkit.createWorld(new WorldCreator(args[1]));
							
							((Player) sender).sendMessage("Loading world " + ChatColor.GREEN + args[1] + ".");
							
							return true;
							
						} else if (args[0].equalsIgnoreCase("unload")) {
							if (Bukkit.getWorld(args[1]).getPlayers().isEmpty()) {
								Bukkit.unloadWorld(args[1], true);
								((Player) sender).sendMessage("Unloading world " + ChatColor.GREEN + args[1] + ".");

							} else {
								((Player) sender).sendMessage("Can not unload a world with players in it.");

							}
							
							return true;
							
						}
						return false;
						
					}
				}
				return false;
		
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


package witpvp;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Wp extends JavaPlugin {
	
	public static Wp plugin;
	
	private static Set<PlayerProfile> playerProfiles = new HashSet<>();
	
	// This method is called when the plugin is enabled
	@Override
	public void onEnable() {
		plugin = this;		

		broadcast(ChatColor.YELLOW + "WitPvP core plugin enabled.");

		new GlobalListener(plugin); // create globallistener to listen for events
		
		initializeCommands(); // see CommandListener
	}
	
	// This method is called when the plugin is disabled
	@Override
	public void onDisable() {			
		broadcast(ChatColor.YELLOW + "WitPvP core plugin enabled.");
		
		plugin = null;
	}
	
	//Bukkit.broadcastMessage does not seem to work anymore, use this instead
	public static void broadcast(String msg, Collection<? extends Player> players) {
		for (Player p : players) {
			p.sendMessage(msg);
		}
	}
	
	public static void broadcast(String msg) {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for (Player p : players) {
			p.sendMessage(msg);
		}
	}
	
	private void initializeCommands() {
		CommandListener cmdListener = new CommandListener();
		getCommand("test").setExecutor(cmdListener);
	}
	
	public static void addPlayerProfile(PlayerProfile profile) {
		playerProfiles.add(profile);
	}
	
	public static void removePlayerProfile(PlayerProfile profile) {
		playerProfiles.remove(profile);
	}
	
	public static boolean hasPlayerProfile(PlayerProfile profile) {
		return playerProfiles.contains(profile);
	}
	
	public static PlayerProfile getPlayerProfile(Player player) {
		for (PlayerProfile p : playerProfiles) {
			if (p.getPlayer().equals(player)) {
				return p;
			}
		}
		return null;
	}
}

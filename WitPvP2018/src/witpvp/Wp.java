package witpvp;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import witpvp.match.Match;

public class Wp extends JavaPlugin {
	
	public static Wp plugin;
	
	//private static ProtocolManager protocolManager;
	
	private static Set<PlayerProfile> playerProfiles = new HashSet<>();
	private static Set<Match> matches = new HashSet<Match>();
	
	// This method is called when the plugin is enabled
	@Override
	public void onEnable() {
		plugin = this;		
		
		broadcast(ChatColor.YELLOW + "WitPvP core plugin enabled.");
		
		//protocolManager = ProtocolLibrary.getProtocolManager(); // API for sending server packets and interacting with player's clients directly
		
		new GlobalListener(plugin); // create globallistener to listen for events TODO temporary
		
		initializeCommands(); // see CommandListener
		loadWorlds(); // make all worlds show up in world list
	
	}
	
	// This method is called when the plugin is disabled
	@Override
	public void onDisable() {			
		broadcast(ChatColor.YELLOW + "WitPvP core plugin disabled.");
		
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
		getCommand("match").setExecutor(cmdListener);
		getCommand("hook").setExecutor(cmdListener);
		getCommand("release").setExecutor(cmdListener);
		getCommand("round").setExecutor(cmdListener);
		getCommand("world").setExecutor(cmdListener);
		getCommand("charge").setExecutor(cmdListener);

	}
	
	private void loadWorlds() {
		Bukkit.createWorld(new WorldCreator("campside"));
		Bukkit.createWorld(new WorldCreator("cave"));
		Bukkit.createWorld(new WorldCreator("crystal"));
		Bukkit.createWorld(new WorldCreator("desert"));
		Bukkit.createWorld(new WorldCreator("empty"));
		Bukkit.createWorld(new WorldCreator("islands"));
		Bukkit.createWorld(new WorldCreator("islands2"));
		Bukkit.createWorld(new WorldCreator("jungle"));
		Bukkit.createWorld(new WorldCreator("main"));
		Bukkit.createWorld(new WorldCreator("lobby"));
		Bukkit.createWorld(new WorldCreator("arena"));

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
		PlayerProfile newProfile = new PlayerProfile(player);
		addPlayerProfile(newProfile);
		return newProfile;
	}
	
	public static Set<Match> getMatches() {
		return new HashSet<Match>(matches);
	}
	
	public static void addMatch(Match match) {
		matches.add(match);
	}

	public static World getLobbyWorld() {
		return Bukkit.getWorld("lobby");
	}
}

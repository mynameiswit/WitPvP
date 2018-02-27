package witpvp;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Wp extends JavaPlugin {
	
	public static Wp plugin;
	
	private static Set<PlayerProfile> profiles = new HashSet<>();
		
	@Override
	public void onLoad() {
	}
	
	@Override
	public void onDisable() {			
		plugin = null;
	}
	
	@Override
	public void onEnable() {
		plugin = this;		
		Bukkit.broadcastMessage(ChatColor.YELLOW + "WitPvP2018 core plugin enabled.");
	}
	
	public static void addPlayerProfile(PlayerProfile profile) {
		profiles.add(profile);
	}
	
	public static void removePlayerProfile(PlayerProfile profile) {
		profiles.remove(profile);
	}
	
	public static boolean hasPlayerProfile(PlayerProfile profile) {
		return profiles.contains(profile);
	}
	
	public static PlayerProfile getPlayerProfile(Player player) {
		for (PlayerProfile p : profiles) {
			if (p.getPlayer().equals(player)) {
				return p;
			}
		}
		return null;
	}
}

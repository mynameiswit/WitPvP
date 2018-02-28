package witpvp.match;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.plugin.Plugin;

import witpvp.PlayerProfile;
import witpvp.Wp;
import witpvp.imports.HotbarMessager;

public class RoundListener implements Listener {
	
	private Round round;
	
	public RoundListener(Round round) {
		Plugin plugin = Wp.plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.round = round;
	}
	
	
}

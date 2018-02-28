package witpvp.match;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import witpvp.Wp;

public class RoundListener implements Listener {
	
	private Round round;
	
	public RoundListener(Round round) {
		Plugin plugin = Wp.plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.round = round;
	}
}

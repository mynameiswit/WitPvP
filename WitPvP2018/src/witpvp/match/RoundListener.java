package witpvp.match;

import org.bukkit.event.Listener;

import witpvp.Wp;

public class RoundListener implements Listener {
	
	private Round round;
	
	public RoundListener(Wp plugin, Round round) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.round = round;
	}
}

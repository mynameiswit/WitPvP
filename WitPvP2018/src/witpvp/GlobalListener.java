package witpvp;

import org.bukkit.event.Listener;

public class GlobalListener implements Listener {
		
	public GlobalListener(Wp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

}

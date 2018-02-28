package witpvp;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class GlobalListener implements Listener {
		
	public GlobalListener(Wp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	// TODO temporary for testing death system
	// this needs to only happen for players who are in a round
	// if someone dies in the lobby world, they should respawn instantly
	// if someone dies in another world and is not in a match, they should also respawn instantly
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (entity.getType() == EntityType.PLAYER) {
			Player player = ((Player) entity);
			
			if (event.getCause() == DamageCause.VOID) {
				event.setDamage(0);
				event.setCancelled(true);
				Death.killPlayer(player);
			}
			
			if (event.getFinalDamage() >= player.getHealth()) {
				event.setCancelled(true); // cancel the damage event	
				Death.killPlayer(player);
			}
		}
	}

}

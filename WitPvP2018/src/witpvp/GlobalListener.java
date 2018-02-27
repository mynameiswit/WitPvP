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

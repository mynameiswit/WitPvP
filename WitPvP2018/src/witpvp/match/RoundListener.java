package witpvp.match;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import witpvp.Death;
import witpvp.Wp;

public class RoundListener implements Listener {
	
	private Round round;
	
	public RoundListener(Wp plugin, Round round) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.round = round;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (entity.getType() == EntityType.PLAYER) {
			Player player = ((Player) entity);
			if (event.getFinalDamage() >= player.getHealth()) {
				event.setCancelled(true);				
				Death.killPlayer(player);
			}
		}
	}
}

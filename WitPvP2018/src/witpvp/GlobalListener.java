package witpvp;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GlobalListener implements Listener {
		
	public GlobalListener(Wp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			
			if (event.getEntity().getWorld() == Wp.getLobbyWorld()) {
				if (event.getCause() == DamageCause.VOID) {
					event.setDamage(0);
					event.setCancelled(true);
					player.teleport(Wp.getLobbyWorld().getSpawnLocation());
					
				} else {
					event.setDamage(0);
					event.setCancelled(true);
					
				}
			} else {
				return;
				
			}	
		}
	}
	
	@EventHandler
	public void onRegain(EntityRegainHealthEvent event) {
		if ((event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) || (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN)) {
			event.setCancelled(true);
			
		}
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		event.setCancelled(true);
		
	}
	
	@EventHandler
	public void onOffHand(PlayerSwapHandItemsEvent event) {
		event.setCancelled(true);
		
	}
}

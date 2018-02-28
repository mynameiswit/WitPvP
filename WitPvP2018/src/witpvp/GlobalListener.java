package witpvp;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GlobalListener implements Listener {
		
	public GlobalListener(Wp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	// TODO temp
	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
		
	}
	
	// TODO temp
	@EventHandler
	public void onAnimate(PlayerAnimationEvent event) {
		if (event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
			if (event.getPlayer().getItemInHand().getType() == Material.BOW) {
				Player player = event.getPlayer();
				PlayerProfile profile = Wp.getPlayerProfile(player);
											
				profile.getBattleProfile().drawCharge();
			}
		}
	}
	
	@EventHandler
	public void onArrowShoot(EntityShootBowEvent event) {
		if (event.getEntity().getType() == EntityType.PLAYER) {
			Player player = ((Player) event.getEntity());
			PlayerProfile profile = Wp.getPlayerProfile(player);
										
			profile.getBattleProfile().drawCharge();
		}
	}
	
	// TODO temp
	@EventHandler
	public void onArrowHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			if (arrow.getShooter() instanceof Player) {
				Player shooter = ((Player) arrow.getShooter());
				ExplosionUtil.create(arrow.getLocation(), 4, 4, shooter, true);
				
				Wp.getPlayerProfile(shooter).getBattleProfile().drawCharge();
			}
		}
	}
	
	// TODO temporary for testing death system
	// TODO Move to a place that makes sense
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
			} else if (event.getCause() == DamageCause.FALL) {
				// Reduced fall damage
				event.setDamage(event.getDamage()/3.5);
				
				if (event.getDamage() < 1) {
					event.setCancelled(true);
				}
				
			}
			
			if (event.getFinalDamage() >= player.getHealth()) {
				event.setCancelled(true); // cancel the damage event	
				Death.killPlayer(player);
			}
		}
	}
	
	// TODO Move to a place that makes sense
	@EventHandler
	public void onRegain(EntityRegainHealthEvent event) {
		if ((event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) || (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN)) {
			event.setCancelled(true);
			
		}
	}
	
	// TODO Move to a place that makes sense
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		event.setCancelled(true);
		
	}
	
	// TODO Move to a place that makes sense
	// Disables off-hand
	@EventHandler
	public void onOffHand(PlayerSwapHandItemsEvent event) {
		event.setCancelled(true);
		
	}

}

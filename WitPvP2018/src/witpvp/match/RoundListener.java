package witpvp.match;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.plugin.Plugin;

import witpvp.Death;
import witpvp.ExplosionUtil;
import witpvp.PlayerProfile;
import witpvp.Wp;

public class RoundListener implements Listener {
	
	private Round round;
	
	private World world;
	private Set<Player> players;
	
	public RoundListener(Round round) {
		Plugin plugin = Wp.plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.round = round;
		
		this.world = round.getWorld();
		this.players = round.getPlayers();
	}
	
	public boolean hasPlayer(Player player) {
		return (player.getWorld() == world && players.contains(player));
		
	}
	
	// TODO temp
	@EventHandler
	public void onAnimate(PlayerItemDamageEvent event) {
		if (hasPlayer(event.getPlayer())) {
			event.setCancelled(true);
			
		}
	}
	
	// TODO temp
	@EventHandler
	public void onAnimate(PlayerAnimationEvent event) {
		if (hasPlayer(event.getPlayer())) {
			if (event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
				if (event.getPlayer().getItemInHand().getType() == Material.BOW) {
					Player player = event.getPlayer();
					PlayerProfile profile = Wp.getPlayerProfile(player);
																
					profile.getBattleProfile().drawCharge();
					profile.getBattleProfile().moveArrowSelection();
	
				}
			}
		}
	}
	
	// TODO temp
	@EventHandler
	public void onArrowShoot(EntityShootBowEvent event) {
		if (event.getEntity().getType() == EntityType.PLAYER) {
			Player player = ((Player) event.getEntity());
			
			if (hasPlayer(player)) {
				PlayerProfile profile = Wp.getPlayerProfile(player);
				
				profile.getBattleProfile().drawCharge();
				
			}
		}
	}
	
	// TODO temp
	@EventHandler
	public void onArrowHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			if (arrow.getShooter() instanceof Player) {
				Player shooter = ((Player) arrow.getShooter());
				
				if (hasPlayer(shooter)) {
					ExplosionUtil.create(arrow.getLocation(), 4, 4, shooter, true);
					
					Wp.getPlayerProfile(shooter).getBattleProfile().drawCharge();
				}	
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
			
			if (hasPlayer(player)) {
				if (event.getCause() == DamageCause.VOID) {
					event.setDamage(0);
					event.setCancelled(true);
					Death.killPlayer(player); // TODO if round hasnt started, should instantly respawn the player instead
					return;
				} 
				
				if (!round.isDamageEnabled()) {
					event.setCancelled(true);
					return;
				}
				
				if (Death.isDead(player))  {
					event.setDamage(0);
					event.setCancelled(true);
					return;
							
				}
				
				if (event.getFinalDamage() >= player.getHealth()) {
					event.setDamage(0);
					event.setCancelled(true);
					Death.killPlayer(player);

				} 		
			}	
		}
	}	
}

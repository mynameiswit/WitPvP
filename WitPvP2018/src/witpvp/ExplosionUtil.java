package witpvp;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class ExplosionUtil {
	
	private final static int VISUAL_RADIUS = 400;
	
	public static final void create(Location center, double radius, double knockback, Player source, boolean friendlyFire) {
		double redux = 4;
		double yLimit = knockback/3.5;
		double height = 0.4;
		double defaultRadius = 4;
		
		//Visuals
		for (Player player : center.getWorld().getPlayers()) {
			player.spigot().playEffect(center, 
					                   Effect.EXPLOSION, 
					                   0, 
					                   0, 
					                   (float)radius/6f, 
					                   (float)radius/8f, 
					                   (float)radius/6f, 
					                   0, 
					                   ((int) radius*7), 
					                   VISUAL_RADIUS);
			player.spigot().playEffect(center, 
	                                   Effect.EXPLOSION_LARGE, 
	                                   0, 
	                                   0, 
	                                   (float)radius/4f, 
					                   (float)radius/6f, 
					                   (float)radius/4f, 
					                   0, 
					                   ((int) radius*2), 
					                   VISUAL_RADIUS);
		}

		// Sound
		for (Player player : center.getWorld().getPlayers()) {
			player.playSound(center, Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 1);
		}
		
		for (Entity nearbyEntity : center.getWorld().getNearbyEntities(center, radius*2 + 1, radius*2 + 1, radius*2 + 1)) {
			if (nearbyEntity instanceof Player) {
				Player target = (Player) nearbyEntity;

				if (target != source || friendlyFire) {
					double dist = Math.abs(center.distance(new Location(target.getWorld(), target.getLocation().getX(), target.getLocation().getY() + target.getEyeHeight()/2, target.getLocation().getZ())));
					
					double distFactor;
					if (dist < radius) {
						distFactor = 1;
						
					} else if (dist >= radius && dist <= 2*radius) {
						distFactor = 1 - Math.abs(dist - radius)/radius;
						distFactor *= Math.max(0, Math.pow(distFactor, 0.2)); // curvature, look GeoGebra
						
					} else {
						distFactor = 0;
						
					}

					// Full power within r.
					// 50% power between 1.5r and r.
					// 0% power at 2r.
					// Affects both knockback and damage.
					
					// Player is within range
					if (distFactor > 0) {
						Vector velocity = new Vector(target.getLocation().getX() - center.getX(), 0.55D + knockback/40, target.getLocation().getZ() - center.getZ());
						velocity.setY(velocity.getY() * 0.8); // Reduce y-component
						velocity.normalize();
						velocity.multiply(distFactor*knockback/redux);	
						
						double charge = Wp.getPlayerProfile(source).getBattleProfile().getCharge();
						velocity.multiply(1+charge/100);
						
						// Limit the y-component of the knockback vector.
						if (Math.abs(velocity.getY()) > yLimit)  {
							velocity.setY(yLimit * Math.signum(velocity.getY()));
							
						}
						
						// Damage
						//WpUtil.damagePlayer(target, source, Math.round(distFactor*damage/Math.max(0, Math.pow(distFactor, 0.2))), DamageType.MAGIC, false);
					
						// Knockback
						target.setVelocity(velocity);
						target.setFallDistance(0); // reset their fall distance, which is used to calculate fall damage
					}
				}
			}
		}
	}
}

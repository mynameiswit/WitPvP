package witpvp;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import witpvp.match.Round;

public final class DoubleJumpListener implements Listener {
	
	private Round round;
	
	private World world;
	private Set<Player> players;
	
	private static HashMap<Player, Long> lastJumpMap = new HashMap<>();
	private static HashMap<Player, Integer> cooldownMap = new HashMap<>();
	
	private final static int DEFAULT_COOLDOWN = 5000;
	private final static double VELOCITY = 1.4;
	
	public DoubleJumpListener(Round round) {
		this.round = round;
		Wp.plugin.getServer().getPluginManager().registerEvents(this, Wp.plugin);
		
		this.world = round.getWorld();
		this.players = round.getPlayers();
		
	}
	
	public boolean hasPlayer(Player player) {
		return (player.getWorld() == world && players.contains(player));
		
	}
	
	public static void addPlayer(Player player) {
		cooldownMap.put(player, DEFAULT_COOLDOWN);
		
	}
	
	public static void putOnCooldown(Player player) {
		lastJumpMap.put(player, System.currentTimeMillis());
		scheduleNotification(player);
		
	}
	
	public static void removeCooldown(Player player) {
		lastJumpMap.remove(player);
		
	}
	
	public static HashMap<Player, Long> getlastJumpMap() {
		return new HashMap<>(lastJumpMap);
		
	}
	
	public static void resetCooldowns() {
		for (Player p : lastJumpMap.keySet()) {
			removeCooldown(p);
			
		}
	}
	
	public static HashMap<Player, Integer> getCooldownMap() {
		return new HashMap<>(cooldownMap);
		
	}
	
	public static int getCooldown(Player player) {
		if (!cooldownMap.containsKey(player)) {
			addPlayer(player);
		}
		
		if (lastJumpMap.containsKey(player)) {
			long current = System.currentTimeMillis();
			long lastJump = lastJumpMap.get(player);
			int cooldown = cooldownMap.get(player);
			
			int timeSinceLastJump = (int) Math.max(current-lastJump, 0);
			
			if (timeSinceLastJump > cooldown) {
				removeCooldown(player);
				
			}
			
			return Math.max(cooldown-timeSinceLastJump, 0);
						
		} else {
			return 0;
			
		}
	}
	
	public static boolean hasCooldown(Player player) {
		if (lastJumpMap.containsKey(player)) {
			long current = System.currentTimeMillis();
			long lastJump = lastJumpMap.get(player);
			int cooldown = cooldownMap.get(player);
			
			if (current - lastJump < cooldown) {
				removeCooldown(player);
				return false;

			} else {
				return true;
				
			}			
			
		} else {
			return false;

		}
	}
	
	public static void scheduleNotification(Player player) {
		int cooldownRemaining = getCooldown(player);
		new BukkitRunnable() {
			@Override
			public void run() {
				//check if the round is still ongoing
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_FLUTE, 0.3f, 0.8f);
			}
		}.runTaskLater(Wp.plugin, 20*cooldownRemaining/1000);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (hasPlayer(player)) {
			if (player.getGameMode() == GameMode.CREATIVE) {
				return;
					
			}
			
			player.setAllowFlight(true);
			
			// Hacky fix to maybe try and fix fall damage again
			/*
			if (!player.isOnGround() && player.getFallDistance() > 2) {
			    Location to = event.getTo().clone().subtract(0, 1, 0); // Get the location they will be at next tick
			    
			    if (to.getBlock().getType() != Material.AIR) { // If that block is not air
					player.setAllowFlight(false); // Cancel their ability to fly so that they take regular fall damage
					
			    }
			}
			*/
		} else {
			return;
		}
	}
	
	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {		
		Player player = event.getPlayer();
		
		if (hasPlayer(player)) {
			if (player.getGameMode() == GameMode.CREATIVE) {
				return;
			}
			
			event.setCancelled(true);
			player.setFlying(false);
			player.setFallDistance(0);
			
			if (event.getPlayer().getLocation().getBlock().isLiquid()) {
				return;
			}
			
			int cooldown = getCooldown(player);
			double cooldownInSec = cooldown/1000d; //rounds to 1 digit
			DecimalFormat oneDigit = new DecimalFormat("#,##0.0"); //format to 1 decimal place
			DecimalFormatSymbols newSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
			newSymbols.setDecimalSeparator('.');
			oneDigit.setDecimalFormatSymbols(newSymbols);
			
			if (cooldown > 0) {
				// Player has double jump on cooldown, send them a msg and return
				player.sendMessage("You can Double Jump in " + ChatColor.GREEN + oneDigit.format(cooldownInSec) + ChatColor.RESET + " seconds.");
				return;
			}
			
			
			Location location = player.getLocation();
			Vector vector = location.getDirection().multiply(VELOCITY).setY(0.5);
		
			player.setVelocity(vector);	
			
			putOnCooldown(player);
			scheduleNotification(player);
							
			// Visuals and sound
			for (Player nearbyPlayer : location.getWorld().getPlayers()) {
				nearbyPlayer.playSound(location, Sound.ENTITY_GHAST_SHOOT, 1, 1.2f);
				nearbyPlayer.spigot().playEffect(location.subtract(0,1d,0), Effect.CLOUD, 0, 0, 0.25F, 0.25F, 0.25F, 0, 15, 400);
				
			}
		} else {
			return;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

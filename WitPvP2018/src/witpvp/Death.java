package witpvp;

import java.util.HashSet;
import java.util.Set;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World.Spigot;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class Death {
	
	private static final int RESPAWN_TIME = 60;
	
	private static final Set<Player> deadPlayers = new HashSet<>();
	
	public static boolean isDead(Player player) {
		return deadPlayers.contains(player);
	}
	
	public static void killPlayer(Player player) {
		killPlayer(player, RESPAWN_TIME);
	}
	
	@SuppressWarnings("deprecation")
	public static void killPlayer(Player player, int respawnTime) {	
		if (!deadPlayers.contains(player)) {
			// Keep track of the dead players
			deadPlayers.add(player);
			
			// Call the event so other listeners know that a player has died
			PlayerDeathEvent event = new PlayerDeathEvent(player, null, 0, "");
			Bukkit.getServer().getPluginManager().callEvent(event);
			
			GameMode gameMode = player.getGameMode();
			float flySpeed = player.getFlySpeed();
			float walkSpeed = player.getWalkSpeed();

			player.setHealth(player.getMaxHealth());
			
			// Sound
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1.0f, 1.0f);
			
			// Visuals
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, respawnTime+20, 0, false, false), true);
			player.setGameMode(GameMode.SPECTATOR);
			Util.hidePlayer(player, player.getWorld().getPlayers());
			
			// Make the player unable to move, and stop moving
			player.setFlySpeed(0);
			player.setWalkSpeed(0);
			player.setVelocity(new Vector(0, 0, 0));
			
			// Draw text on screen, and update the timer
			player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "You have died", ChatColor.WHITE + "Respawning in " + ChatColor.GOLD + respawnTime/20 + ChatColor.WHITE + " seconds...");
			new BukkitRunnable() {
				int ticksRemaining = respawnTime;
				@Override
				public void run() {
					if (ticksRemaining > 40) {
						ticksRemaining -= 20;
						player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "You have died", ChatColor.WHITE + "Respawning in " + ChatColor.GOLD + ticksRemaining/20 + ChatColor.WHITE + " seconds...");
					} else {
						player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "You have died", ChatColor.WHITE + "Respawning in " + ChatColor.GOLD + "1" + ChatColor.WHITE + " seconds...");
						this.cancel();
					}
				}
			}.runTaskTimer(Wp.plugin, 20, 20);	
			
			// When timer expires, restore player attributes and respawn
			new BukkitRunnable() {
				@Override
				public void run() {
					Util.showPlayer(player, player.getWorld().getPlayers());
					
					player.setFlySpeed(flySpeed);
					player.setWalkSpeed(walkSpeed);
					player.setGameMode(gameMode);
					
					respawnPlayer(player);
					deadPlayers.remove(player);
				}
			}.runTaskLater(Wp.plugin, respawnTime);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void respawnPlayer(Player player) {
			// Set to normal combat stats
			Util.resetAttributes(player);
			
			//Util.showPlayer(player, player.getWorld().getPlayers());
			
			// Teleport player to the world's spawnpoint
			// (REPLACE WITH REAL RESPAWN CODE LATER)
			Location spawnLocation = player.getWorld().getSpawnLocation();
			player.teleport(spawnLocation);
						
	}
}

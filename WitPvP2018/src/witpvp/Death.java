package witpvp;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class Death {
	
	private static final int RESPAWN_TIME = 60;
	
	private static final Set<Player> deadPlayers = new HashSet<>();
	
	public static void killPlayer(Player player) {
		killPlayer(player, RESPAWN_TIME);
	}
	
	@SuppressWarnings("deprecation")
	public static void killPlayer(Player player, int respawnTime) {	
		if (!deadPlayers.contains(player)) {
			deadPlayers.add(player);
			
			PlayerDeathEvent event = new PlayerDeathEvent(player, null, 0, "");
			Bukkit.getServer().getPluginManager().callEvent(event);
			
			GameMode gameMode = player.getGameMode();
			float flySpeed = player.getFlySpeed();

			Util.hidePlayer(player, player.getWorld().getPlayers());
			player.setHealth(player.getMaxHealth());
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1.0f, 1.0f);
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, respawnTime+20, 0, false, false), true);
			player.setGameMode(GameMode.SPECTATOR);
			player.setFlySpeed(0);
			player.setVelocity(new Vector(0, 0, 0));
			
			player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "You have died", ChatColor.WHITE + "Respawning in " + ChatColor.GOLD + respawnTime/20 + ChatColor.WHITE + " seconds..." , 5, respawnTime-10, 0);
			new BukkitRunnable() {
				int ticksRemaining = respawnTime;
				@Override
				public void run() {
					if (ticksRemaining > 40) {
						ticksRemaining -= 20;
						player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "You have died", ChatColor.WHITE + "Respawning in " + ChatColor.GOLD + ticksRemaining/20 + ChatColor.WHITE + " seconds..." , 0, 21, 0);
					} else {
						player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "You have died", ChatColor.WHITE + "Respawning in " + ChatColor.GOLD + "1" + ChatColor.WHITE + " seconds..." , 0, 20, 5);
						this.cancel();
					}
				}
			}.runTaskTimer(Wp.plugin, 20, 20);	
			
			new BukkitRunnable() {
				@Override
				public void run() {
					player.setGameMode(gameMode);
					player.setFlySpeed(flySpeed);
					
					deadPlayers.remove(player);
					respawnPlayer(player);
				}
			}.runTaskLater(Wp.plugin, respawnTime);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void respawnPlayer(Player player) {
		
			Util.setAttributes(player, PlayerAttributes.DEFAULT);
			player.teleport(player.getBedSpawnLocation());
			player.playSound(player.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_CAST_SPELL, 0.5f, 1.0f);
			
	}
}

package witpvp;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public final class DoubleJumpListener implements Listener {
	
	private static HashMap<Player, Long> jumpTimeMap = new HashMap<>();
	private static HashMap<Player, Integer> cooldownMap = new HashMap<>();
	
	private final static int DEFAULT_COOLDOWN = 5000;
	
	public DoubleJumpListener(Wp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	public static void addPlayer(Player player, long cooldown) {
		cooldownMap.put(player, DEFAULT_COOLDOWN);
	}
	
	public static void putOnCooldown(Player player) {
		jumpTimeMap.replace(player, System.currentTimeMillis());
	}
	
	public static void removeCooldown(Player player) {
		jumpTimeMap.remove(player);
	}
	
	public static HashMap<Player, Long> getJumpTimeMap() {
		return new HashMap<>(jumpTimeMap);
	}
	
	public static void resetCooldowns() {
		for (Player p : jumpTimeMap.keySet()) {
			removeCooldown(p);
		}
	}
	
	public static HashMap<Player, Integer> getCooldownMap() {
		return new HashMap<>(cooldownMap);
	}
	
	public static boolean onCooldown(Player player) {
		if (jumpTimeMap.containsKey(player)) {
			long current = System.currentTimeMillis();
			long jumpTime = jumpTimeMap.get(player);
			int cooldown = cooldownMap.get(player);
			
			if (current - jumpTime < cooldown) {
				removeCooldown(player);
				return false;

			} else {
				return true;
				
			}
			
			
		} else {
			return false;

		}
	}
}

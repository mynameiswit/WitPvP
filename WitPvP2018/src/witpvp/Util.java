package witpvp;

import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import witpvp.constants.GameConstants;

public final class Util {
	@SuppressWarnings("deprecation")
	public static void hidePlayer(Player player, Collection<? extends Player> players) {
		player.setCollidable(false);
		for (Player p : players) {
			if (player != p) {
				p.hidePlayer(player);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void showPlayer(Player player, Collection<? extends Player> collection) {
		player.setCollidable(true);
		for (Player p : collection) {
			if (player != p) {
				p.showPlayer(player);
			}
		}
	}
	
	public static final void clearInventory(Player player) {
		player.getInventory().clear();
        player.getInventory().setArmorContents(null);
		player.getInventory().setHeldItemSlot(0);
	}
	
	@SuppressWarnings("deprecation")
	public static final void resetAttributes(Player player) {
		float walkSpeed = 0.2f;
		GameMode gameMode = GameMode.ADVENTURE;
		
		clearInventory(player);
		player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        player.setWalkSpeed(walkSpeed);
        //player.setFlySpeed(flySpeed);
        player.setGameMode(gameMode);
        player.setFoodLevel(20);
        removeAllPotionEffects(player);
    }
	
	public static final void removeAllPotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

}

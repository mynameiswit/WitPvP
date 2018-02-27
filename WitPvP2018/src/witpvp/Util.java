package witpvp;

import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import witpvp.constants.GameConstants;

public final class Util {
	@SuppressWarnings("deprecation")
	public static void hidePlayer(Player player, Collection<? extends Player> players) {
		for (Player p : players) {
			p.hidePlayer(player);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void showPlayer(Player player, Collection<? extends Player> collection) {
		for (Player p : collection) {
			p.showPlayer(player);
		}
	}
	
	public static final void clearInventory(Player player) {
		player.getInventory().clear();
        player.getInventory().setArmorContents(null);
		player.getInventory().setHeldItemSlot(0);
	}
	
	@SuppressWarnings("deprecation")
	public static final void setAttributes(Player player, PlayerAttributes attributes) {
		double maxHealth = 20;
		float walkSpeed = 0.2f;
		GameMode gameMode = GameMode.SURVIVAL;
		
		switch (attributes) {
		case IN_LOBBY: 
			gameMode = GameMode.ADVENTURE;
			break;
		case IN_ROUND: 
			gameMode = GameMode.ADVENTURE;
			maxHealth = GameConstants.Attributes.MAX_HEALTH;
			break;
		default:
			break;
		}
		
		player.setMaxHealth(maxHealth);
        player.setHealth(player.getMaxHealth());
        player.setWalkSpeed(walkSpeed);
        player.setGameMode(gameMode);
        removeAllPotionEffects(player);
    }
	
	public static final void removeAllPotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
			//player.addPotionEffect(new PotionEffect(effect.getType(), 0, effect.getAmplifier()), true);
		}
	}

}

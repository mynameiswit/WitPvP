package witpvp.constants;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class GameConstants {
	
	public static class Attributes {
		public static final double MAX_HEALTH = 20;
		public static final float WALK_SPEED = 0.2F + 0.05F; // Default 0.2
	}
	
	public static class Combat {
		public static final int CRITICAL_CHANCE = 50;
		public static final double CRITICAL_MULTIPLER = 1.4;
		public static final double BLOCK_MULTIPLIER = 0.3;
		
		// Perfect Shot
		public static final int PERFECT_SHOT_CHANCE_BASE = 40;
		public static final int PERFECT_SHOT_DISTANCE_BASE = 30;
		public static final double PERFECT_SHOT_DAMAGE = 7;
	}

	public static class Potions {
		public static final PotionEffect PERFECT_SHOT_POTIONEFFECT = new PotionEffect(PotionEffectType.SLOW, 40, 3);
		public static final PotionEffect SPRINT_ARROW_HIT_POTIONEFFECT = new PotionEffect(PotionEffectType.SLOW, 35, 2);
		public static final int POTION_DURATION = 600;
	}
	
	// Other
	public static final int STONE_SWORD_CHANCE = 20;
	public static final int ARROW_COUNT_SPAWN = 20;
	public static final int ROUND_START_DELAY = 200;
	
}

package witpvp.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class KnockbackSource {
	
	private Entity entity;
	
	public KnockbackSource(Player player) {
		this.entity = player;
	}
	
	public KnockbackSource(Entity entity) {
		this.entity = entity;
	}
}

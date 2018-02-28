package witpvp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

public class PlayerKnockbackEvent extends Event implements Cancellable {
	
	private boolean cancelled = false;
	private Vector vector;
	private Player player;
	
	public PlayerKnockbackEvent(Player player, Vector vector, KnockbackSource source) {
		this.vector = vector;
		this.player = player;
	}
	
	public Vector getVector() {
		return vector;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}

}

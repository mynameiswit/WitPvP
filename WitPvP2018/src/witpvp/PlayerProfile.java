package witpvp;

import org.bukkit.entity.Player;

public class PlayerProfile {
	private Player player;
	
	public PlayerProfile(Player player) {
		setPlayer(player);
	}
	
	public void setPlayer(Player player) {
		this.player = player;
		Wp.addPlayerProfile(this);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void remove() {
		this.player = null;
		Wp.removePlayerProfile(this);
	}
}

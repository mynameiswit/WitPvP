package witpvp;

import org.bukkit.entity.Player;

public class PlayerProfile {
	private Player player;
	private BattleProfile battleProfile;
	
	public PlayerProfile(Player player) {
		setPlayer(player);
		this.battleProfile = new BattleProfile(player);
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
	
	public BattleProfile getBattleProfile() {
		return battleProfile;
	}
	
	public void setBattleProfile(BattleProfile battleProfile) {
		this.battleProfile = battleProfile;
	}
}

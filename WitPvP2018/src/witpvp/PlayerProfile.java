package witpvp;

import org.bukkit.entity.Player;

import witpvp.match.Match;
import witpvp.match.Round;

public class PlayerProfile {
	private Player player;
	private BattleProfile battleProfile;
	
	private Match match;
	private Round round;
	
	public PlayerProfile(Player player) {
		setPlayer(player);
		this.battleProfile = new BattleProfile(player);
	}
	
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
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

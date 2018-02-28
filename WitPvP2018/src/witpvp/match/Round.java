package witpvp.match;

import org.bukkit.event.HandlerList;

import witpvp.Competition;
import witpvp.Wp;

public class Round extends Competition {
			
	private Match match;
	
	private MatchSettings settings;
	private RoundStatus status = RoundStatus.IDLE;
	
	private RoundListener listener;
	
	private boolean damageEnabled = false;
	
	public Round(Match match) {
		this.match = match;
		this.settings = match.getSettings();
		this.teams = match.getTeams();
	}
	
	public void start() {
		setStatus(RoundStatus.PLAYING);
		match.setStatus(MatchStatus.PLAYING_ROUND);
		
		enableDamage();
		
		listener = new RoundListener(this);
		
		// teleport all players to arena
		// set correct hp
	}
	
	private void enableDamage() {
		damageEnabled = true;
	}
	
	private void disableDamage() {
		damageEnabled = false;
	}

	public void end() {
		setStatus(RoundStatus.IDLE);
		match.setStatus(MatchStatus.BETWEEN_ROUNDS);

		disableDamage();
		
		HandlerList.unregisterAll(listener);
		
		// compare the kills of all teams
		// teleport all players to lobby world?
	}
	
	public MatchSettings getMatchSettings() {
		return settings;
	}
	
	public RoundStatus getStatus() {
		return status;
	}
	
	public void setStatus(RoundStatus status) {
		this.status = status;
	}
}

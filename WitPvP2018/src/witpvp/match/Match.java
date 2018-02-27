package witpvp.match;

import java.util.ArrayList;
import java.util.List;

import witpvp.Competition;

public class Match extends Competition {
			
	private List<Round> rounds = new ArrayList<>();
	
	private MatchSettings settings;
	
	public Match() {
		this.settings = new MatchSettings();
	}
		
	public Match(MatchSettings settings) {
		this.settings = settings;
	}

	public Round newRound() {
		Round round = new Round(this);
		addRound(round);
		return round;
	}
	
	public void end() {
		
	}
	
	private void addRound(Round round) {
		rounds.add(round);
	}
	
	public MatchSettings getSettings() {
		return settings;
	}

	public List<Round> getRounds() {
		return new ArrayList<>(rounds);
	}
}

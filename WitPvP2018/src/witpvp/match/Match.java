package witpvp.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import witpvp.Competition;
import witpvp.Wp;

public class Match extends Competition {
	
	private int id;
	private List<Round> rounds = new ArrayList<>();
	
	private MatchSettings settings;
	private MatchStatus status = MatchStatus.NOT_YET_STARTED;
	
	public Match() {
		this.id = generateID(this);
		this.settings = new MatchSettings();
		
		Wp.addMatch(this);
	}
	
	public void setStatus(MatchStatus status) {
		this.status = status;
	}
	
	public MatchStatus getStatus() {
		return status;
	}

	public Round newRound() {
		Round round = new Round(this);
		addRound(round);
		return round;
	}
	
	public static int generateID(Match match) {
		Random rnd = new Random();
		int id = 0;
		boolean unique = true;
		
		do {
			id = rnd.nextInt(89)+10;
			for (Match activeMatch : Wp.getMatches()) {
				if (activeMatch != match && activeMatch.getID() == id) {
					unique = false;
				}
			}
		} while (unique == false);

		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public void end() {
		status = MatchStatus.FINISHED;
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

	public static Match getMatch(int id) {
		for (Match activeMatch : Wp.getMatches()) {
			if (activeMatch.getID() == id) return activeMatch;
		}
		return null;
	}
	
	public boolean hasActiveRound() {
		boolean result = false;
		
		for (Round r : rounds) {
			if (r.getStatus() == RoundStatus.PLAYING) {
				result = true;
				break;
				
			}
		}
		
		return result;
	}

	public Round getActiveRound() {
		if (hasActiveRound()) {
			Round activeRound = null;
			
			for (Round r : rounds) {
				if (r.getStatus() == RoundStatus.PLAYING) {
					activeRound = r;
					break;
					
				}
			}
			
			return activeRound;
			
		} else {
			return null;
			
		}
	}
	
	public Round getLatestRound() {
		if (rounds.size() > 0) {
			return rounds.get(rounds.size()-1);
		} else {
			return null;
		}
		
	}
	
}

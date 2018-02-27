package witpvp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

public abstract class Competition {
	
	protected List<Team> teams = new ArrayList<>();

	public List<Team> getTeams() {
		return new ArrayList<>(teams);
	}
	
	public Team createTeam() {
		Team team = new Team();
		return team;
	}
	
	public void addTeam(Team team) {
		Set<Player> players = team.getPlayers();
		for (Player p : players) {
			if (hasPlayer(p)) {
				return;
			}
		}
		teams.add(team);
	}
	
	public void removeTeam(Team team) {
		teams.remove(team);
	}
	
	public void addPlayer(Player player) {
		if (!hasPlayer(player)) {
			Team team = createTeam();
			team.addPlayer(player);
			addTeam(team);
		}
	}
	
	public boolean hasPlayer(Player player) {
		return getPlayers().contains(player);
	}
	
	public Set<Player> getPlayers() {
		Set<Player> players = new HashSet<>();
		for (Team t : teams) {
			players.addAll(t.getPlayers());
		}
		return players;
	}
	
}

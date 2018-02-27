package witpvp;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

public class Team {

	private Set<Player> players = new HashSet<>();
		
	public Team() {
		
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public boolean hasPlayer(Player player) {
		return players.contains(player);
	}
	
	public void remove() {
		this.players = null;
	}

	public Set<Player> getPlayers() {
		return new HashSet<>(players);
	}
}

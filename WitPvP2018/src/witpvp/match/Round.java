package witpvp.match;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import witpvp.Competition;
import witpvp.DoubleJumpListener;
import witpvp.Util;
import witpvp.Wp;

public class Round extends Competition {
			
	private Match match;
	private MatchSettings settings;
	private RoundStatus status = RoundStatus.IDLE;
	private World world;
	private boolean damageEnabled = false;
	
	private RoundListener roundListener;
	private DoubleJumpListener doubleJumpListener;
	private Set<Listener> listeners;
	
	public Round(Match match) {
		this.match = match;
		this.settings = match.getSettings().copy();
		this.teams = match.getTeams();
		
		//TODO temp
		this.world = Bukkit.getWorld("arena");
		
		listeners = new HashSet<>();
		roundListener = new RoundListener(this);
		doubleJumpListener = new DoubleJumpListener(this);
		listeners.add(roundListener);
		listeners.add(doubleJumpListener);
		
	}
	
	public void start() {
		setStatus(RoundStatus.PLAYING);
		match.setStatus(MatchStatus.PLAYING_ROUND);
		
		enableDamage();

		for (Player p : getPlayers()) {
			p.setAllowFlight(true);
		}
		
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
		setStatus(RoundStatus.FINISHED);
		match.setStatus(MatchStatus.BETWEEN_ROUNDS);

		disableDamage();
		
		for (Player p : getPlayers()) {
			p.setAllowFlight(false);
			
		}
	}
	
	public void destroy() {
		unregisterListeners();
	}
	
	private void unregisterListeners() {
		for (Listener listener : listeners) {
			HandlerList.unregisterAll(listener);
		}
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

	public void teleportToMap(Player player) {
		// TODO temp before map system is implemented
		player.teleport(world.getSpawnLocation());
		Util.clearInventory(player);

	}
	
	public void teleportToLobby(Player player) {
		// TODO temp before map system is implemented
		player.teleport(Wp.getLobbyWorld().getSpawnLocation());
		Util.resetAttributes(player);
		Util.clearInventory(player);
		
		for (Player p : getPlayers()) {
			p.setAllowFlight(false);
			
		}
		
	}

	public World getWorld() {
		return world;
	}

	public boolean isDamageEnabled() {
		return damageEnabled;
	}
}

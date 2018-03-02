package witpvp;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class HealthBar {
	
	private BossBar bossBar;
	private Player owner;
	
	private final static double MAX_HEALTH = 20.0;
	
	@SuppressWarnings("deprecation")
	public HealthBar(Player owner) {
		this.owner = owner;
		this.bossBar = Bukkit.createBossBar(getTitle(), getColor(), getStyle());
		updateHealth();
		
	}

	private BarStyle getStyle() {
		return BarStyle.SEGMENTED_10;
		
	}

	private BarColor getColor() {
		return BarColor.RED;
		
	}

	private String getTitle() {
		return owner.getDisplayName();
		
	}
	
	public void updateHealth() {
		setHealth(owner.getHealth());
		
	}
	
	public void setHealth(double d) {
		bossBar.setProgress(d/MAX_HEALTH);
		
	}
	
	public Player getOwner() {
		return owner;
		
	}
	
	public List<Player> getPlayers() {
		return bossBar.getPlayers();
		
	}
	
	public boolean hasPlayer(Player player) {
		return bossBar.getPlayers().contains(player);
		
	}
	
	public void addPlayer(Player player) {
		updateHealth();
		bossBar.addPlayer(player);
	}
	
	public void removePlayer(Player player) {
		updateHealth();
		bossBar.removePlayer(player);
		
	}
	
	public void removeAll() {
		updateHealth();
		for (Player p : bossBar.getPlayers()) {
			bossBar.removePlayer(p);
			
		}
	}

	public double getHealth() {
		return 20*bossBar.getProgress();
	}
}

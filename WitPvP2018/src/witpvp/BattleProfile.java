package witpvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import witpvp.imports.HotbarMessager;

public class BattleProfile {
	private Player player;
	private PlayerProfile profile;
	
	double charge = 0;
	final double MAXIMUM_CHARGE = 100;
	
	public BattleProfile(Player player) {
		profile = Wp.getPlayerProfile(player);
		this.player = player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setCharge(double d) {
		if (d < 0) {
			charge = 0;
		} else {
			charge = Math.min(d, MAXIMUM_CHARGE);
		}
	}
	
	public void drawCharge() {
		String block = "";
		String power = "§lPOWER" + ChatColor.RESET + ": ";
		String percentage = "\uFE6A";
		String value = Long.toString(Math.round(charge));
		ChatColor color = ChatColor.GRAY;
		
		int amountOfColoredBlocks = ((Long) Math.round(charge/10)).intValue();
				
		if (amountOfColoredBlocks <= 2) {
			color = ChatColor.WHITE;
		} else if (amountOfColoredBlocks > 2 && amountOfColoredBlocks <= 4) {
			color = ChatColor.RED;
		} else if (amountOfColoredBlocks > 4 && amountOfColoredBlocks <= 6) {
			color = ChatColor.YELLOW;
		} else if (amountOfColoredBlocks > 6 && amountOfColoredBlocks <= 8) {
			color = ChatColor.GOLD;
		} else if (amountOfColoredBlocks > 8) {
			color = ChatColor.GREEN;
		}
		
		block += color;
		for (int i = 1; i <= amountOfColoredBlocks; i++ ) {
			block += "\u2B1B";
		}
		block += ChatColor.GRAY;
		for (int i = amountOfColoredBlocks; i < 10; i++ ) {
			block += "\u2B1B";
		}

		//HotbarMessager.sendHotBarMessage(player, "hello");
		HotbarMessager.sendHotBarMessage(player, power + color + block + " " + ChatColor.RESET + value + percentage);

	}

	public double getCharge() {
		return charge;
	}
	
}

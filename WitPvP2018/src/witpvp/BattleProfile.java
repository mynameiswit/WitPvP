package witpvp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import witpvp.imports.HotbarMessager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class BattleProfile {
	
	private Player player;
	private PlayerProfile playerProfile;
	
	private HealthBar healthBar;
	
	private double charge = 0;
	private int selectedArrow = 0;
	public final double MAXIMUM_CHARGE = 100;
	
	public BattleProfile(Player player) {
		playerProfile = Wp.getPlayerProfile(player);
		this.player = player;
		this.healthBar = new HealthBar(player);
	}
	
	public PlayerProfile getPlayerProfile() {
		return playerProfile;
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
	
	public HealthBar getHealthBar() {
		return healthBar;
	}
	
	public int getSelectedArrow() {
		return selectedArrow;
		
	}
	
	public void moveArrowSelection() {
		if (selectedArrow >= 2) {
			selectedArrow = 0;
			
		} else {
			selectedArrow++;
			
		}
		
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HAT, 1.0f, 0.8f);
		
		ProtocolManager protocolLib = ProtocolLibrary.getProtocolManager();
		ItemStack item = new ItemStack(Material.BOW);
		ItemMeta itemMeta = item.getItemMeta();
		
		switch (selectedArrow) {
		case 0:
			itemMeta.setDisplayName(ChatColor.WHITE + "§lBasic Arrow");
			break;
		case 1:
			itemMeta.setDisplayName(ChatColor.YELLOW + "§lExplosive Shot");
			break;
		case 2:
			itemMeta.setDisplayName(ChatColor.RED + "§lRocket Shot");
			break;
		}
		
		item.setItemMeta(itemMeta);

		player.getInventory().setItemInMainHand(item);
		
		
		/*
		PacketContainer equipmentPacket = protocolLib.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
		equipmentPacket.getIntegers().write(0, player.getEntityId());
		equipmentPacket.getItemSlots().write(0, ItemSlot.MAINHAND);
		equipmentPacket.getItemModifier().write(0, player.getInventory().getItemInMainHand());
											
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(player, equipmentPacket);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet " + equipmentPacket, e);
		}
		*/
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

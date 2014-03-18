package me.assist.lazertag.player;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerData {

	private HashMap<String, Location> oldLocations = new HashMap<String, Location>();

	private HashMap<String, GameMode> oldGameModes = new HashMap<String, GameMode>();
	
	private HashMap<String, ItemStack[]> inventoryContents = new HashMap<String, ItemStack[]>();
	private HashMap<String, ItemStack[]> armorContents = new HashMap<String, ItemStack[]>();

	private static PlayerData playerData;

	public static PlayerData getInstance() {
		if (playerData == null)
			playerData = new PlayerData();
		return playerData;
	}

	public void prepareAddPlayer(Player player) {
		oldLocations.put(player.getName(), player.getLocation());
		
		oldGameModes.put(player.getName(), player.getGameMode());

		inventoryContents.put(player.getName(), player.getInventory().getContents());
		armorContents.put(player.getName(), player.getInventory().getArmorContents());

		resetPlayer(player);
	}

	public void prepareRemovePlayer(Player player) {
		resetPlayer(player);

		player.teleport(oldLocations.get(player.getName()));
		oldLocations.remove(player.getName());
		
		player.setGameMode(oldGameModes.get(player.getName()));
		oldGameModes.remove(player.getName());

		player.getInventory().setContents(inventoryContents.get(player.getName()));
		inventoryContents.remove(player.getName());

		player.getInventory().setArmorContents(armorContents.get(player.getName()));
		armorContents.remove(player.getName());
	}

	@SuppressWarnings("deprecation")
	public static void resetPlayer(Player player) {
		for (PotionEffect type : player.getActivePotionEffects()) {
			if (type != null) {
				player.removePotionEffect(type.getType());
			}
		}

		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.updateInventory();

		player.setLevel(0);
		player.setExp(0f);
		player.setHealth(20.0);
		player.setFoodLevel(10);
	}
}

package me.assist.lazertag.util;

import me.assist.lazertag.arena.Arena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectClassGUI {

	@SuppressWarnings("deprecation")
	public static void openGUI(Player player, Arena arena) {		
		Inventory inv = Bukkit.createInventory(null, 9, arena.getName());

		ItemStack blue = ItemUtil.createItemStack(new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getWoolData()), ChatColor.GRAY + "Blue", "&6Click to join the &9Blue &6team.");
		ItemStack red = ItemUtil.createItemStack(new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData()), ChatColor.GRAY + "Red", "&6Click to join the &cRed &6team.");
		
		ItemStack air = ItemUtil.createItemStack(new ItemStack(Material.PISTON_MOVING_PIECE), ChatColor.GRAY + "Empty", "&6Nothing to see here.");

		inv.setItem(0, air);
		inv.setItem(1, air);
		inv.setItem(2, air);
		inv.setItem(3, blue);
		inv.setItem(4, air);
		inv.setItem(5, red);
		inv.setItem(6, air);
		inv.setItem(7, air);
		inv.setItem(8, air);

		player.openInventory(inv);
	}
}

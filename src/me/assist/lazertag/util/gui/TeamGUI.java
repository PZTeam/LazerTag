package me.assist.lazertag.util.gui;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.util.ItemUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TeamGUI {

	@SuppressWarnings("deprecation")
	public static void openGUI(Player player, Arena arena) {		
		Inventory inv = Bukkit.createInventory(null, 9, arena.getName());

		ItemStack blue = ItemUtil.createItemStack(new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getWoolData()), ChatColor.GRAY + "Blue", "&6Click to join the &9Blue &6team.");
		ItemStack red = ItemUtil.createItemStack(new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData()), ChatColor.GRAY + "Red", "&6Click to join the &cRed &6team.");

		inv.setItem(3, blue);
		inv.setItem(5, red);

		player.openInventory(inv);
	}
}

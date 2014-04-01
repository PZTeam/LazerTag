package me.assist.lazertag.util.gui;

import me.assist.lazertag.util.ItemUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopGUI {

	public static void openGUI(Player player) {		
		Inventory inv = Bukkit.createInventory(null, 9, "Shop");

		ItemStack doublePoints1 = ItemUtil.createItemStack(new ItemStack(Material.GOLD_INGOT), ChatColor.GRAY + "2x Points", "&cEarn double points with this upgrade!", "", "&7Cost: &6100 Points", "&7Duration: &61 Game");
		ItemStack doublePoints2 = ItemUtil.createItemStack(new ItemStack(Material.GOLD_BLOCK), ChatColor.GRAY + "2x Points", "&cEarn double points with this upgrade!", "", "&7Cost: &6450 Points", "&7Duration: &65 Games");
		
		inv.setItem(3, doublePoints1);
		inv.setItem(5, doublePoints2);
		player.openInventory(inv);
	}
}

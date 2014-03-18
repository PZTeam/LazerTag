package me.assist.lazertag.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import me.assist.lazertag.LazerTag;
import me.assist.lazertag.arena.Arena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponUtil {

	static ItemStack stack = null;

	public static synchronized void giveWeapon(Arena arena) {
		for (Object s : arena.getPlayers().keySet().toArray()) {
			Player p = Bukkit.getPlayerExact((String) s);

			if (p != null && !p.isDead()) {
				p.getInventory().clear();
				p.getInventory().addItem(build());
			}
		}
	}

	public static ItemStack build() {
		if (stack == null) {
			Material material = null;

			try {
				material = Material.getMaterial(LazerTag.getInstance().getConfig().getString("laser.type"));
			} catch (Exception ex) {
				LazerTag.getInstance().log(Level.WARNING, "Gun type is invalid - using default type (WOOD_HOE)");
				material = Material.WOOD_HOE;
			}

			ItemStack stack = new ItemStack(material);
			ItemMeta meta = stack.getItemMeta();

			meta.setDisplayName(colorize(LazerTag.getInstance().getConfig().getString("laser.meta.name", "Laser")));
			List<String> lores = new ArrayList<String>();

			if (LazerTag.getInstance().getConfig().contains("laser.meta.lores") && LazerTag.getInstance().getConfig().isList("laser.meta.lores")) {
				for (String lore : LazerTag.getInstance().getConfig().getStringList("laser.meta.lores")) {
					lores.add(colorize(lore));
				}
			}

			meta.setLore(lores);
			stack.setItemMeta(meta);

			WeaponUtil.stack = stack;
		}

		return stack;
	}

	public static String colorize(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
}

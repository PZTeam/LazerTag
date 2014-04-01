package me.assist.lazertag.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	public static ItemStack createItemStack(ItemStack stack, String name, String... lores) {
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(WeaponUtil.colorize(name));

		List<String> loreList = new ArrayList<String>();

		for (String lore : lores) {
			loreList.add(WeaponUtil.colorize(lore));
		}

		meta.setLore(loreList);
		
		stack.setItemMeta(meta);
		return stack;
	}
}

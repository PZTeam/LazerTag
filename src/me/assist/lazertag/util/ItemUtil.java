package me.assist.lazertag.util;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	public static ItemStack createItemStack(ItemStack stack, String name, String lore) {
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(WeaponUtil.colorize(name));
		meta.setLore(Arrays.asList(WeaponUtil.colorize(lore)));
		
		stack.setItemMeta(meta);
		return stack;
	}
}

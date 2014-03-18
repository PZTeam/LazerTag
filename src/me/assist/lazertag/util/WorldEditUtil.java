package me.assist.lazertag.util;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class WorldEditUtil {

	private static WorldEditPlugin worldEditPlugin;

	public void setupWorldEdit(PluginManager pm) {
		Plugin p = pm.getPlugin("WorldEdit");
		
		if ((p != null) && ((p instanceof WorldEditPlugin)))
			worldEditPlugin = (WorldEditPlugin) p;
	}

	public static WorldEditPlugin getWorldEdit() {
		return worldEditPlugin;
	}
}

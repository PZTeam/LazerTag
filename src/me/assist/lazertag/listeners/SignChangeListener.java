package me.assist.lazertag.listeners;

import me.assist.lazertag.Header;
import me.assist.lazertag.SignWall;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaFile;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.util.LocationUtil;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

	@EventHandler
	public void onChange(SignChangeEvent event) {
		String[] lines = event.getLines();

		if (lines[0].equalsIgnoreCase("[LazerTag]")) {
			if (!lines[2].equals("") || !lines[2].equals(" ")) {
				String id = lines[2];
				Arena arena = ArenaManager.getInstance().getArena(id);

				if (arena != null) {
					ArenaFile arenaFile = new ArenaFile(id);

					event.setLine(1, "Click to Join");
					event.setLine(3, "0/" + arena.getMaxPlayers());
					
					SignWall.updateSign((Sign) event.getBlock().getState());

					if (!arenaFile.getConfig().contains("signs"))
						arenaFile.getConfig().set("signs.1.location", LocationUtil.locationToString(event.getBlock().getLocation(), true));
					else
						arenaFile.getConfig().set("signs." + (arenaFile.getConfig().getConfigurationSection("signs").getKeys(false).size() + 1) + ".location", LocationUtil.locationToString(event.getBlock().getLocation(), true));

					arenaFile.save();

					event.getPlayer().sendMessage(Header.POSITIVE + "Lobby sign created for arena " + ChatColor.DARK_GREEN + id + ChatColor.GREEN + ".");
				} else {
					event.getPlayer().sendMessage(Header.NEGATIVE + "The arena " + ChatColor.DARK_RED + id + ChatColor.RED + " doesn't exist.");
				}
			}
		}
	}
}

package me.assist.lazertag.commands;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaFile;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.util.LocationUtil;
import me.assist.lazertag.util.WorldEditUtil;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

public class CreateArena implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (args.length == 1) {
			Arena arena = ArenaManager.getInstance().getArena(args[0]);

			if (arena == null) {
				Selection sel = WorldEditUtil.getWorldEdit().getSelection(player);

				if (sel != null) {
					Location start = new Location(player.getWorld(), sel.getNativeMinimumPoint().getBlockX(), sel.getNativeMinimumPoint().getBlockY(), sel.getNativeMinimumPoint().getBlockZ());
					Location end = new Location(player.getWorld(), sel.getNativeMaximumPoint().getBlockX(), sel.getNativeMaximumPoint().getBlockY(), sel.getNativeMaximumPoint().getBlockZ());
					
					ArenaManager.getInstance().createArena(args[0]);
					ArenaFile file = new ArenaFile(args[0]);
					
					file.getConfig().set("region.start", LocationUtil.locationToString(start, true));
					file.getConfig().set("region.end", LocationUtil.locationToString(end, true));

					file.save();

					player.sendMessage(Header.POSITIVE + "Arena " + ChatColor.DARK_GREEN + args[0] + ChatColor.GREEN + " created!");
				} else {
					player.sendMessage(Header.NEGATIVE + "WorldEdit selection required!");
				}
				
			} else {
				player.sendMessage(Header.NEGATIVE + "The arena '" + ChatColor.DARK_RED + args[0] + ChatColor.RED + "' already exists!");
			}

		} else {
			player.sendMessage(ChatColor.DARK_RED + "Syntax error: " + ChatColor.RED + "/lt createarena <name>");
		}

		return true;
	}

	@Override
	public String permission() {
		return "lt.createarena";
	}
}

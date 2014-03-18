package me.assist.lazertag.commands;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.arena.Team;
import me.assist.lazertag.util.LocationUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetSpawn implements SubCommand {
	
	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (args.length == 2) {
			Arena arena = ArenaManager.getInstance().getArena(args[0]);
			
			if (arena != null) {
				if(args[1].equalsIgnoreCase("red"))
					LocationUtil.setSpawn(arena, Team.RED, player.getLocation());
				else if(args[1].equalsIgnoreCase("blue"))
					LocationUtil.setSpawn(arena, Team.BLUE, player.getLocation());
				else return true;
				
				player.sendMessage(Header.POSITIVE + "Team spawn set!");
			} else {
				player.sendMessage(Header.NEGATIVE + "The arena '" + ChatColor.DARK_RED + args[0] + ChatColor.RED + "' doesn't exist.");
			}
			
		} else {
			player.sendMessage(ChatColor.DARK_RED + "Syntax error: " + ChatColor.RED + "/lt setspawn <arena> <red|blue>");
		}
		
		return true;
	}

	@Override
	public String permission() {
		return "lt.setspawn";
	}
}

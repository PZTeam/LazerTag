package me.assist.lazertag.commands;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.game.GameManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ForceStart implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (args.length == 1) {
			Arena arena = ArenaManager.getInstance().getArena(args[0]);

			if (arena != null) {
				GameManager.getInstance().startCountdown(arena);
			} else {
				player.sendMessage(Header.NEGATIVE + "The arena '" + ChatColor.DARK_RED + args[0] + ChatColor.RED + "' doesn't exist!");
			}
			
		} else {
			player.sendMessage(ChatColor.DARK_RED + "Syntax error: " + ChatColor.RED + "/lt forcestart <arena>");
		}
		
		return true;
	}

	@Override
	public String permission() {
		return "lt.forcestart";
	}
}

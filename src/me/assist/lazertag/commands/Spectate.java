package me.assist.lazertag.commands;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Spectate implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (args.length == 1) {
			Arena arena = ArenaManager.getInstance().getArena(args[0]);

			if (arena != null) {
				if (arena.isSpectatable()) {
					if (!arena.containsSpectator(player)) {
						if (!arena.containsPlayer(player)) {
							arena.addSpectator(player);

							player.sendMessage(Header.POSITIVE + "You have started spectating the game.");
						} else {
							player.sendMessage(Header.NEGATIVE + "You are already playing a game!");
						}
						
					} else {
						player.sendMessage(Header.NEGATIVE + "You are already spectating a game!");
					}

				} else {
					player.sendMessage(Header.NEGATIVE + "This arena is currently not spectatable.");
				}

			} else {
				player.sendMessage(Header.NEGATIVE + "Invalid arena.");
			}

		} else {
			player.sendMessage(ChatColor.DARK_RED + "Syntax error: " + ChatColor.RED + "/lt spectate <arena>");
		}

		return true;
	}

	@Override
	public String permission() {
		return "lt.spectate";
	}
}

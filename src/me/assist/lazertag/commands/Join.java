package me.assist.lazertag.commands;

import java.util.logging.Level;

import me.assist.lazertag.Header;
import me.assist.lazertag.LazerTag;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.arena.ArenaState;
import me.assist.lazertag.util.gui.TeamGUI;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Join implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (args.length == 1) {
			Arena arena = ArenaManager.getInstance().getArena(args[0]);

			if (arena != null) {
				if (arena.isJoinable()) {
					if (!arena.containsPlayer(player)) {
						TeamGUI.openGUI(player, arena);
					} else {
						player.sendMessage(Header.NEGATIVE + "You are already playing a game!");
					}

				} else {
					if (arena.getState() == ArenaState.PLAYING) {
						player.sendMessage(Header.NEGATIVE + "This game is already running, please choose another one.");
					} else {
						LazerTag.getInstance().log(Level.SEVERE, "Something went wrong!");
					}
				}

			} else {
				player.sendMessage(Header.NEGATIVE + "Invalid arena.");
			}

		} else {
			player.sendMessage(ChatColor.DARK_RED + "Syntax error: " + ChatColor.RED + "/lt join <arena>");
		}

		return true;
	}

	@Override
	public String permission() {
		return "lt.join";
	}
}

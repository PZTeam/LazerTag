package me.assist.lazertag.commands;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.game.GameManager;
import me.assist.lazertag.game.StopReason;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DeleteArena implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (args.length == 1) {
			Arena arena = ArenaManager.getInstance().getArena(args[0]);

			if (arena != null) {
				if (GameManager.getInstance().isActive(arena))
					GameManager.getInstance().stopGame(arena, StopReason.FORCE, null);

				ArenaManager.getInstance().deleteArena(args[0]);
				player.sendMessage(Header.POSITIVE + "Arena " + ChatColor.DARK_GREEN + args[0] + ChatColor.GREEN + " deleted!");

			} else {
				player.sendMessage(Header.NEGATIVE + "The arena '" + ChatColor.DARK_RED + args[0] + ChatColor.RED + "' doesn't exist!");
			}

		} else {
			player.sendMessage(ChatColor.DARK_RED + "Syntax error: " + ChatColor.RED + "/lt deletearena <name>");
		}

		return true;
	}

	@Override
	public String permission() {
		return "lt.deletearena";
	}
}
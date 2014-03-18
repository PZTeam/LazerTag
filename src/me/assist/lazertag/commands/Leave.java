package me.assist.lazertag.commands;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.entity.Player;

public class Leave implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;
		
		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			if (arena.containsPlayer(player)) {
				arena.removePlayer(player);
				player.sendMessage(Header.POSITIVE + "You have left the game.");
				return true;
			}
			
			if(arena.containsSpectator(player)) {
				arena.removeSpectator(player);
				player.sendMessage(Header.POSITIVE + "You have stoppped spectating the game.");
				return true;
			}
		}

		player.sendMessage(Header.NEGATIVE + "You are not in a game!");
		return true;
	}

	@Override
	public String permission() {
		return "lt.leave";
	}
}

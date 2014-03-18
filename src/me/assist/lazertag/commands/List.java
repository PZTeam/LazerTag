package me.assist.lazertag.commands;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class List implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		StringBuilder sb = new StringBuilder();
		boolean zero = true;

		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			sb.append(arena.isJoinable() ? ChatColor.GREEN + arena.getName() : ChatColor.RED + arena.getName()).append(ChatColor.GRAY + ", ");

			zero = false;
		}

		if (!zero)
			player.sendMessage(Header.NEUTRAL + "Arenas: " + sb.toString().substring(0, sb.toString().length() - 2));
		else
			player.sendMessage(Header.NEGATIVE + "No arenas were found.");
		
		return true;
	}

	@Override
	public String permission() {
		return "lt.list";
	}
}

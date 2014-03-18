package me.assist.lazertag.commands;

import java.util.Map;
import java.util.Map.Entry;

import me.assist.lazertag.Header;
import me.assist.lazertag.player.PlayerStatManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Top implements SubCommand {

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("kills")) {
				Map<String, Integer> sorted = PlayerStatManager.getInstance().topKills();

				if (sorted.size() > 0) {
					player.sendMessage(Header.NEUTRAL + "Displaying top 10 kills:");

					for (int i = 0; i < (sorted.size() > 10 ? 10 : sorted.size()); i++) {
						Entry<String, Integer> entry = (Entry<String, Integer>) sorted.entrySet().toArray()[i];
						player.sendMessage(Header.NEUTRAL.toString() + (i + 1) + ". " + ChatColor.BLUE + entry.getKey() + ChatColor.GRAY + ": " + ChatColor.BLUE + entry.getValue());
					}

				} else {
					player.sendMessage(Header.NEGATIVE + "No data to display.");
				}
			}

			if (args[0].equalsIgnoreCase("deaths")) {
				Map<String, Integer> sorted = PlayerStatManager.getInstance().topDeaths();

				if (sorted.size() > 0) {
					player.sendMessage(Header.NEUTRAL + "Displaying top 10 deaths:");

					for (int i = 0; i < (sorted.size() > 10 ? 10 : sorted.size()); i++) {
						Entry<String, Integer> entry = (Entry<String, Integer>) sorted.entrySet().toArray()[i];
						player.sendMessage(Header.NEUTRAL.toString() + (i + 1) + ". " + ChatColor.BLUE + entry.getKey() + ChatColor.GRAY + ": " + ChatColor.BLUE + entry.getValue());
					}

				} else {
					player.sendMessage(Header.NEGATIVE + "No data to display.");
				}
			}
		}

		return true;
	}

	@Override
	public String permission() {
		return "lt.top";
	}
}

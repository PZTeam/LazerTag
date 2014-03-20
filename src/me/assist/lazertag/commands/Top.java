package me.assist.lazertag.commands;

import java.util.Map;
import java.util.Map.Entry;

import me.assist.lazertag.Header;
import me.assist.lazertag.managers.PlayerManager;

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
				Map<String, Integer> sorted = PlayerManager.getInstance().topKills();

				if (sorted.size() > 0) {
					player.sendMessage(Header.NEUTRAL + "Displaying top 10 kills:");

					for (int i = 0; i < (sorted.size() > 10 ? 10 : sorted.size()); i++) {
						Entry<String, Integer> entry = (Entry<String, Integer>) sorted.entrySet().toArray()[i];
						player.sendMessage(Header.NEUTRAL.toString() + (i + 1) + ". " + ChatColor.BLUE + entry.getKey() + ChatColor.GRAY + ": " + ChatColor.BLUE + entry.getValue());
					}

				} else {
					player.sendMessage(Header.NEGATIVE + "No data to display.");
				}

			} else if (args[0].equalsIgnoreCase("deaths")) {
				Map<String, Integer> sorted = PlayerManager.getInstance().topDeaths();

				if (sorted.size() > 0) {
					player.sendMessage(Header.NEUTRAL + "Displaying top 10 deaths:");

					for (int i = 0; i < (sorted.size() > 10 ? 10 : sorted.size()); i++) {
						Entry<String, Integer> entry = (Entry<String, Integer>) sorted.entrySet().toArray()[i];
						player.sendMessage(Header.NEUTRAL.toString() + (i + 1) + ". " + ChatColor.BLUE + entry.getKey() + ChatColor.GRAY + ": " + ChatColor.BLUE + entry.getValue());
						//player.sendMessage(Header.NEUTRAL.toString() + (i + 1) + ". " + ChatColor.BLUE + (sorted.size() <= i ? "Empty" : sorted.keySet().toArray()[i]) + ChatColor.GRAY + ": " + ChatColor.BLUE + (sorted.size() <= i ? "0" : sorted.values().toArray()[i]));
					}

				} else {
					player.sendMessage(Header.NEGATIVE + "No data to display.");
				}

			} else {
				player.sendMessage(ChatColor.DARK_RED + "Syntax error: " + ChatColor.RED + "/lt top <kills|deaths>");
			}
		}

		return true;
	}

	@Override
	public String permission() {
		return "lt.top";
	}
}

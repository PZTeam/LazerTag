package me.assist.lazertag.commands;

import me.assist.lazertag.Header;
import me.assist.lazertag.managers.PlayerManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Me implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (args.length == 0) {
			int kills = PlayerManager.getInstance().getKills(player.getName());
			int deaths = PlayerManager.getInstance().getDeaths(player.getName());

			float ratio = 0.0f;

			if (kills != 0 && deaths == 0)
				ratio = kills;
			else if (kills != 0 && deaths != 0)
				ratio = kills / deaths;

			int points = PlayerManager.getInstance().getPoints(player.getName());

			player.sendMessage(Header.NEUTRAL + "Your stats:");
			player.sendMessage(ChatColor.GRAY + "Kills: " + ChatColor.BLUE + kills);
			player.sendMessage(ChatColor.GRAY + "Deaths: " + ChatColor.BLUE + deaths);
			player.sendMessage(ChatColor.GRAY + "Ratio: " + ChatColor.BLUE + ratio);
			player.sendMessage(ChatColor.GRAY + "Points: " + ChatColor.BLUE + points);
		}

		return true;
	}

	@Override
	public String permission() {
		return "lt.me";
	}
}

package me.assist.lazertag.commands;

import me.assist.lazertag.LazerTag;
import me.assist.lazertag.util.Updater;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Update implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (LazerTag.getInstance().updateAvailable()) {
			player.sendMessage(ChatColor.DARK_PURPLE + "Downloading update " + LazerTag.getInstance().getPluginName() + ".");
			new Updater(LazerTag.getInstance(), LazerTag.getInstance().fileId, LazerTag.getInstance().getPluginFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
		} else {
			player.sendMessage(ChatColor.RED + "No update available.");
		}

		return true;
	}

	@Override
	public String permission() {
		return "lt.update";
	}
}

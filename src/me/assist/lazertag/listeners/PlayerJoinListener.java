package me.assist.lazertag.listeners;

import me.assist.lazertag.LazerTag;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();

		if (p.isOp() && LazerTag.getInstance().updateAvailable()) {
			p.sendMessage(ChatColor.DARK_PURPLE + "An update is available: " + LazerTag.getInstance().getPluginName() + ", a " + LazerTag.getInstance().getReleaseType() + " for " + LazerTag.getInstance().getVersion() + " available at " + LazerTag.getInstance().getLink());
			p.sendMessage(ChatColor.DARK_PURPLE + "Type /lazertag update if you would like to automatically update.");
		}
	}
}

package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.arena.Team;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();

		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			if (arena.containsPlayer(p)) {
				event.setCancelled(true);

				arena.broadcastMessage((arena.getTeam(p) == Team.BLUE ? ChatColor.BLUE : ChatColor.RED) + p.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
				break;
			}
		}
	}
}

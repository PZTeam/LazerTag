package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.arena.Team;
import me.assist.lazertag.managers.PlayerManager;

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

				arena.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + PlayerManager.getInstance().getPoints(p.getName()) + ChatColor.GRAY + "] " + (arena.getTeam(p) == Team.BLUE ? ChatColor.BLUE : ChatColor.RED) + p.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
				break;
			}
		}
	}
}

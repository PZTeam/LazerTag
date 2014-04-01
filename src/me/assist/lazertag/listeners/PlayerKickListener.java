package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener {

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			if (arena.containsPlayer(event.getPlayer())) {
				arena.removePlayer(event.getPlayer());
				return;
			}

			if (arena.containsSpectator(event.getPlayer())) {
				arena.removeSpectator(event.getPlayer());
			}
		}
	}
}

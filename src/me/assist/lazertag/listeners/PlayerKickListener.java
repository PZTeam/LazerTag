package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.managers.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener {

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (PlayerManager.getInstance().isSetup(event.getPlayer().getName()))
			PlayerManager.getInstance().save1_2(event.getPlayer().getName());
		
		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			if (arena.containsPlayer(event.getPlayer())) {
				arena.removePlayer(event.getPlayer());
				return;
			}
			
			if(arena.containsSpectator(event.getPlayer())) {
				arena.removeSpectator(event.getPlayer());
			}
		}
	}
}

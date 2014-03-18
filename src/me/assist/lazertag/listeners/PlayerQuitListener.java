package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.player.PlayerStatManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (PlayerStatManager.getInstance().isSetup(event.getPlayer().getName()))
			PlayerStatManager.getInstance().saveSpecific(event.getPlayer().getName());
		
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

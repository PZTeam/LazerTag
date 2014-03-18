package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.util.LocationUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public void onREspawn(PlayerRespawnEvent event) {
		Player p = event.getPlayer();
		
		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			if (arena.containsPlayer(p)) {
				event.setRespawnLocation(LocationUtil.getSpawn(arena, arena.getTeam(p)));
				break;
			}
		}
	}
}

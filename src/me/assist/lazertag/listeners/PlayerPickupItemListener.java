package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener implements Listener {

	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		
		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			if (arena.containsSpectator(player)) {
				event.setCancelled(true);
				break;
			}
		}
	}
}

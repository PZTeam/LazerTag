package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener implements Listener {

	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		if (ArenaManager.getInstance().c(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
}

package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (ArenaManager.getInstance().c(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
}

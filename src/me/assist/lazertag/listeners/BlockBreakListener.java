package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			if (arena.containsPlayer(player) || arena.containsSpectator(player)) {
				event.setCancelled(true);
				break;
			}
		}
	}
}

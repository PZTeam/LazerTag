package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();

		if (e instanceof Player) {
			Player v = (Player) e;

			for (Arena arena : ArenaManager.getInstance().getArenas()) {
				if (arena.containsSpectator(v)) {
					event.setCancelled(true);
					break;
				}
			}
		}
	}
}

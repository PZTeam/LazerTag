package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		Entity e = event.getEntity();

		if (e instanceof Player) {
			Player v = (Player) e;

			for (Arena arena : ArenaManager.getInstance().getArenas()) {
				if (arena.containsPlayer(v)) {
					if (event.getDamager() != null && event.getDamager() instanceof Player) {
						Player d = (Player) event.getDamager();

						if (arena.containsPlayer(d)) {
							if (arena.getTeam(v) == arena.getTeam(d)) {
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
}

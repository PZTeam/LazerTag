package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.game.ScoreManager;
import me.assist.lazertag.player.PlayerStatManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		Entity e = event.getEntity();

		if (e instanceof Player) {
			Player v = (Player) e;

			for (Arena arena : ArenaManager.getInstance().getArenas()) {
				if (arena.containsPlayer(v)) {
					if (v.getKiller() != null && v.getKiller() instanceof Player) {
						Player k = (Player) v.getKiller();

						if (arena.containsPlayer(k)) {
							if (arena.getTeam(v) != arena.getTeam(k)) {
								ScoreManager.getInstance().applyPoint(arena, arena.getTeam(k));

								PlayerStatManager.getInstance().addKill(k);
								PlayerStatManager.getInstance().addDeath(v);

								arena.broadcastMessage(ChatColor.BLUE + v.getName() + ChatColor.GRAY + " was killed by " + ChatColor.BLUE + k.getName() + ChatColor.GRAY + "!");
								break;
							}
						}
					}

					event.getDrops().clear();
				}
			}
		}
	}
}

package me.assist.lazertag.listeners;

import java.util.Random;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.arena.ArenaState;
import me.assist.lazertag.arena.Team;
import me.assist.lazertag.util.LocationUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (event.getTo().getX() != event.getFrom().getX() || event.getTo().getZ() != event.getFrom().getZ()) {
			for (Arena arena : ArenaManager.getInstance().getArenas()) {
				if (arena.containsPlayer(player) || arena.containsSpectator(player)) {
					if (arena.getState() != ArenaState.PLAYING) {
						event.setTo(event.getFrom());
					} else {
						if (!LocationUtil.isInCuboid(player, arena.getStartBlock(), arena.getEndBlock())) {
							player.teleport(LocationUtil.getSpawn(arena, arena.getTeam(player) == null ? Team.values()[new Random().nextInt(Team.values().length)] : arena.getTeam(player)));

							player.sendMessage(Header.NEUTRAL + "Leaving too early?");
						}
					}
					
					break;
				}
			}
		}
	}
}

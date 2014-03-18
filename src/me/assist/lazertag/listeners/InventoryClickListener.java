package me.assist.lazertag.listeners;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.arena.Team;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onTryClassSelect(InventoryClickEvent event) {
		Player p = ((Player) event.getWhoClicked());

		String id = event.getInventory().getName();
		Arena arena = ArenaManager.getInstance().getArena(id);

		if (arena != null) {
			if (event.isLeftClick()) {
				int slot = event.getSlot();

				if (slot == 3) {
					arena.addPlayer(p, ok(arena, Team.BLUE) ? Team.BLUE : Team.RED);
				} else if (slot == 5) {
					arena.addPlayer(p, ok(arena, Team.RED) ? Team.RED : Team.BLUE);
				} else {
					event.setCancelled(true);
					return;
				}

				p.sendMessage(Header.POSITIVE + "You have joined the " + (arena.getTeam(p) == Team.RED ? ChatColor.RED + "Red" : ChatColor.BLUE + "Blue") + ChatColor.GREEN + " team.");
				p.closeInventory();
			}
			
			event.setCancelled(true);
		}
	}

	private boolean ok(Arena arena, Team team) {
		int alpha = 0;
		int beta = 0;

		for (Team t : arena.getPlayers().values()) {
			if (t == Team.RED)
				alpha++;
			else
				beta++;
		}

		return ((team == Team.RED && (alpha < beta || alpha == beta)) || (team == Team.BLUE && (beta < alpha || beta == alpha)));
	}
}

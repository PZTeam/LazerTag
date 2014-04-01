package me.assist.lazertag.listeners;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.arena.Team;
import me.assist.lazertag.managers.PlayerManager;
import me.assist.lazertag.player.PlayerFile;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onTryClassSelect(InventoryClickEvent event) {
		Player p = ((Player) event.getWhoClicked());

		String title = event.getInventory().getName();
		int slot = event.getSlot();

		Arena arena = ArenaManager.getInstance().getArena(title);

		if (event.isLeftClick()) {
			if (arena != null) {
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

				event.setCancelled(true);

			} else {
				if (title.equals("Shop")) {
					PlayerFile file = new PlayerFile(p.getName());
					int points = PlayerManager.getInstance().getPoints(p.getName());
					boolean changesMade = false;
					
					if (slot == 3) {
						int cost = 100;

						if (points >= cost) {
							PlayerManager.getInstance().addDoublePoints(p.getName(), 1);
							PlayerManager.getInstance().removePoints(p.getName(), cost);

							p.sendMessage(Header.POSITIVE + "You have bought the" + ChatColor.GOLD + " 2x Points (1 Game) " + ChatColor.GREEN + "upgrade!");
							changesMade = true;
						} else {
							p.sendMessage(Header.NEGATIVE + "You don't have enough points to buy this upgrade. You need " + ChatColor.DARK_RED + (cost - PlayerManager.getInstance().getPoints(p.getName())) + ChatColor.RED + " more points.");
						}

					} else if (slot == 5) {
						int cost = 450;

						if (points >= cost) {
							PlayerManager.getInstance().addDoublePoints(p.getName(), 5);
							PlayerManager.getInstance().removePoints(p.getName(), cost);

							p.sendMessage(Header.POSITIVE + "You have bought the" + ChatColor.GOLD + " 2x Points (5 Games) " + ChatColor.GREEN + "upgrade!");
							changesMade = true;
						} else {
							p.sendMessage(Header.NEGATIVE + "You don't have enough points to buy this upgrade. You need " + ChatColor.DARK_RED + (cost - PlayerManager.getInstance().getPoints(p.getName())) + ChatColor.RED + " more points.");
						}

					} else {
						event.setCancelled(true);
						return;
					}

					if(changesMade)
						file.save();

					event.setCancelled(true);
					p.closeInventory();
				}
			}
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

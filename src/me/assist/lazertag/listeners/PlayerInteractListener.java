package me.assist.lazertag.listeners;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.util.LocationUtil;
import me.assist.lazertag.util.WeaponUtil;

import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) event.getClickedBlock().getState();

				if (LocationUtil.isLobbySign(sign)) {
					String[] lines = sign.getLines();

					if (lines[0].equalsIgnoreCase("[LazerTag]")) {
						String id = lines[2];

						Arena arena = ArenaManager.getInstance().getArena(id);

						if (arena != null) {
							p.chat("/lt join " + arena.getName());
						}
					}
					
					event.setCancelled(true);
				}
			}

		} else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
			ItemStack gun = WeaponUtil.build();

			if (p.getInventory().getItemInHand().equals(gun)) {
				if (ArenaManager.getInstance().c(p)) {
					Arrow a = p.launchProjectile(Arrow.class);
					a.setVelocity(a.getVelocity().multiply(2D));
				}
			}
		}
	}
}

package me.assist.lazertag.commands;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.util.gui.ShopGUI;

import org.bukkit.entity.Player;

public class Shop implements SubCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission(permission()))
			return true;

		if (args.length == 0) {
			if(!ArenaManager.getInstance().c(player)) {
				ShopGUI.openGUI(player);
			} else {
				player.sendMessage(Header.NEGATIVE + "You can't do this right now.");
			}
		}

		return true;
	}

	@Override
	public String permission() {
		return "lt.shop";
	}
}

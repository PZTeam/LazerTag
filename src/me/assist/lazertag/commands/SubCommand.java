package me.assist.lazertag.commands;

import org.bukkit.entity.Player;

public interface SubCommand {

	boolean execute(Player player, String[] args);
	String permission();
}

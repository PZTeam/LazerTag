package me.assist.lazertag;

import org.bukkit.ChatColor;

public enum Header {

	POSITIVE(LazerTag.getInstance().getPrefix() + ChatColor.GREEN),
	NEUTRAL(LazerTag.getInstance().getPrefix() + ChatColor.GRAY),
	NEGATIVE(LazerTag.getInstance().getPrefix() + ChatColor.RED);

	private String text = "";

	private Header(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}

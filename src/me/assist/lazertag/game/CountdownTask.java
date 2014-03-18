package me.assist.lazertag.game;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;

import org.bukkit.ChatColor;

public class CountdownTask implements Runnable, Task {

	private Arena arena;

	private int time = 5;
	private int taskId = 0;

	public CountdownTask(Arena arena) {
		this.arena = arena;
	}

	@Override
	public void run() {
		int cur = time;

		if (cur > 10 && cur % 5 == 0) {
			arena.broadcastMessage(Header.NEUTRAL + "Game starting in " + ChatColor.BLUE + cur + ChatColor.GRAY + " seconds!");
		}

		if (cur <= 10) {
			arena.broadcastMessage(Header.NEUTRAL + "Game starting in " + ChatColor.BLUE + cur + ChatColor.GRAY + " second" + (cur == 1 ? "" : "s") + "!");
		}

		if (time <= 0) {
			GameManager.getInstance().stopCountdown(arena);
		}

		time--;
	}

	public Arena getArena() {
		return arena;
	}

	public int getId() {
		return taskId;
	}

	public void setId(int id) {
		this.taskId = id;
	}
}

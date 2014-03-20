package me.assist.lazertag.game.tasks;

import me.assist.lazertag.Header;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.Team;
import me.assist.lazertag.game.GameManager;
import me.assist.lazertag.game.StopReason;
import me.assist.lazertag.managers.BoardManager;
import me.assist.lazertag.managers.ScoreManager;

import org.bukkit.ChatColor;

public class GameTask implements Runnable, Task {

	private Arena arena;

	private int time = 600;
	private int taskId = 0;

	public GameTask(Arena arena) {
		this.arena = arena;
	}

	@Override
	public void run() {
		int cur = time;

		if (cur % 60 == 0 && cur != 0) {
			arena.broadcastMessage(Header.NEUTRAL + "" + ChatColor.BLUE + cur / 60 + ChatColor.GRAY + " minute" + (cur == 60 ? "" : "s") + " left!");
		}

		if (cur == 30 || cur == 20) {
			arena.broadcastMessage(Header.NEUTRAL + "" + ChatColor.BLUE + cur + ChatColor.GRAY + " seconds left!");
		}

		if (cur <= 10) {
			arena.broadcastMessage(Header.NEUTRAL + "" + ChatColor.BLUE + cur + ChatColor.GRAY + " second" + (cur == 1 ? "" : "s") + " left!");
		}

		if (time <= 0) {
			GameManager.getInstance().stopGame(arena, StopReason.TIME_LIMIT_REACHED, ScoreManager.getInstance().lead(getArena()));
		}

		time--;
		BoardManager.getInstance().updateScoreboard(getArena(), Team.BLUE, time);
		BoardManager.getInstance().updateScoreboard(getArena(), Team.RED, time);
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

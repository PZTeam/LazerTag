package me.assist.lazertag.game;

import java.util.HashMap;

import me.assist.lazertag.Header;
import me.assist.lazertag.LazerTag;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaState;
import me.assist.lazertag.arena.Team;
import me.assist.lazertag.game.tasks.CountdownTask;
import me.assist.lazertag.game.tasks.GameTask;
import me.assist.lazertag.game.tasks.Task;
import me.assist.lazertag.managers.BoardManager;
import me.assist.lazertag.managers.ScoreManager;
import me.assist.lazertag.managers.PlayerManager;
import me.assist.lazertag.util.WeaponUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class GameManager {

	private HashMap<Arena, CountdownTask> countdownTasks = new HashMap<Arena, CountdownTask>();
	private HashMap<Arena, GameTask> gameTasks = new HashMap<Arena, GameTask>();

	private static GameManager gameManager;

	public static GameManager getInstance() {
		if (gameManager == null)
			gameManager = new GameManager();
		return gameManager;
	}

	public void startCountdown(Arena arena) {
		CountdownTask task = new CountdownTask(arena);
		task.setId(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(LazerTag.getInstance(), task, 0L, 20L));
		countdownTasks.put(arena, task);

		arena.setState(ArenaState.STARTING);
	}

	public void startGame(Arena arena) {
		GameTask task = new GameTask(arena);
		task.setId(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(LazerTag.getInstance(), task, 0L, 20L));
		gameTasks.put(arena, task);

		ScoreManager.getInstance().createScoreHandler(arena);
		BoardManager.getInstance().createScoreboard(arena);

		for (Object s : arena.getPlayers().keySet().toArray()) {
			PlayerManager.getInstance().setupStat(Bukkit.getPlayerExact((String) s));
		}

		arena.setState(ArenaState.PLAYING);
		arena.broadcastMessage(Header.NEUTRAL + "The game has begun!");

		WeaponUtil.giveWeapon(arena);
		
		arena.u();
	}

	public void stopCountdown(Arena arena) {
		CountdownTask task = getCountdownTask(arena);

		if (task != null) {
			stopTask(task);
			startGame(arena);
		}
	}

	public void stopGame(Arena arena, StopReason reason, Team winner) {
		GameTask task = getArenaTask(arena);

		if (task != null) {
			stopTask(task);

			if (reason == StopReason.TIME_LIMIT_REACHED)
				arena.broadcastMessage(Header.NEUTRAL + "Time limit reached! Team " + (winner == Team.RED ? ChatColor.RED + "Red" : ChatColor.BLUE + "Blue") + ChatColor.GRAY + " has won the game with total of " + ChatColor.BLUE + ScoreManager.getInstance().getScore(arena, winner) + ChatColor.GRAY + " points!");
			else if (reason == StopReason.SCORE_LIMIT_REACHED)
				arena.broadcastMessage(Header.NEUTRAL + "Score limit reached! Team " + (winner == Team.RED ? ChatColor.RED + "Red" : ChatColor.BLUE + "Blue") + ChatColor.GRAY + " has won the game!");
			else
				arena.broadcastMessage(Header.NEUTRAL + "The game has ended.");

			PlayerManager.getInstance().save1(arena, winner, true);
			PlayerManager.getInstance().save1(arena, winner == Team.BLUE ? Team.RED : Team.BLUE, false);
			arena.reset();
		}
	}

	public boolean isActive(Arena arena) {
		return getArenaTask(arena) != null;
	}

	public CountdownTask getCountdownTask(Arena arena) {
		return countdownTasks.get(arena);
	}

	public GameTask getArenaTask(Arena arena) {
		return gameTasks.get(arena);
	}

	public void stopTask(Task task) {
		Bukkit.getServer().getScheduler().cancelTask(task.getId());

		if (gameTasks.containsValue(task))
			gameTasks.remove(task.getArena());
		else if (countdownTasks.containsValue(task))
			countdownTasks.remove(task.getArena());
	}
}

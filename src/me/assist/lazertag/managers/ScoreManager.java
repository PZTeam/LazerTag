package me.assist.lazertag.managers;

import java.util.HashMap;
import java.util.Random;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.Team;
import me.assist.lazertag.game.GameManager;
import me.assist.lazertag.game.StopReason;

public class ScoreManager {

	private HashMap<Arena, HashMap<Team, Integer>> boardMap = new HashMap<Arena, HashMap<Team, Integer>>();

	private static ScoreManager scoreManager;

	public static ScoreManager getInstance() {
		if (scoreManager == null)
			scoreManager = new ScoreManager();
		return scoreManager;
	}

	public void createScoreHandler(Arena arena) {
		resetScores(arena);

		HashMap<Team, Integer> map = new HashMap<Team, Integer>();
		map.put(Team.BLUE, 0);
		map.put(Team.RED, 0);

		boardMap.put(arena, map);
	}

	public void resetScores(Arena arena) {
		if (boardMap.containsKey(arena)) {
			boardMap.remove(arena);
		}
	}

	public void applyScore(Arena arena, Team t) {
		boardMap.get(arena).put(t, boardMap.get(arena).containsKey(t) ? boardMap.get(arena).get(t) + 1 : 1);
		
		if (getScore(arena, t) >= arena.getFile().getConfig().getInt("scoreLimit")) {
			GameManager.getInstance().stopGame(arena, StopReason.SCORE_LIMIT_REACHED, t);
		}
	}

	public int getScore(Arena arena, Team t) {
		return getScores(arena).get(t);
	}

	public HashMap<Team, Integer> getScores(Arena arena) {
		return boardMap.get(arena);
	}

	public Team lead(Arena arena) {
		if (getScore(arena, Team.BLUE) > getScore(arena, Team.RED))
			return Team.BLUE;
		else if (getScore(arena, Team.RED) > getScore(arena, Team.BLUE))
			return Team.RED;
		else
			return Team.values()[new Random().nextInt(Team.values().length)];
	}
}

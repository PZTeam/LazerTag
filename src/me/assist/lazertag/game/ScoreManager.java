package me.assist.lazertag.game;

import java.util.HashMap;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.Team;

public class ScoreManager {

	private HashMap<Arena, HashMap<Team, Integer>> scoreList = new HashMap<Arena, HashMap<Team, Integer>>();

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
		
		scoreList.put(arena, map);
	}

	public void resetScores(Arena arena) {
		if (scoreList.containsKey(arena)) {
			scoreList.remove(arena);
			createScoreHandler(arena);
		}
	}

	public void applyPoint(Arena arena, Team t) {
		scoreList.get(arena).put(t, scoreList.get(arena).containsKey(t) ? scoreList.get(arena).get(t) + 1 : 1);
		
		if(getScore(arena, t) >= arena.getFile().getConfig().getInt("scoreLimit")) {
			GameManager.getInstance().stopGame(arena, StopReason.SCORE_LIMIT_REACHED, t);
		}
	}

	public int getScore(Arena arena, Team t) {
		return getScores(arena).get(t);
	}

	public HashMap<Team, Integer> getScores(Arena arena) {
		return scoreList.get(arena);
	}
	
	public Team lead(Arena arena) {
		return getScore(arena, Team.BLUE) > getScore(arena, Team.RED) ? Team.BLUE : Team.RED;
	}
}

package me.assist.lazertag;

import java.util.HashMap;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.Team;
import me.assist.lazertag.game.ScoreManager;
import me.assist.lazertag.util.TimeUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class BoardManager {

	private HashMap<Arena, HashMap<Team, Scoreboard>> boardList = new HashMap<Arena, HashMap<Team, Scoreboard>>();

	private static BoardManager instance;

	public static BoardManager getInstance() {
		if (instance == null)
			instance = new BoardManager();
		return instance;
	}

	public void createScoreboard(Arena arena) {
		HashMap<Team, Scoreboard> map = new HashMap<Team, Scoreboard>();
		
		// board 1 - not cloneable :(
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = board.registerNewObjective("red", "dummy");

		o.setDisplayName(ChatColor.RED + "Red Team");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		map.put(Team.RED, board);
		// board 1 end

		// board 2
		Scoreboard board2 = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o2 = board2.registerNewObjective("blue", "dummy");

		o2.setDisplayName(ChatColor.BLUE + "Blue Team");
		o2.setDisplaySlot(DisplaySlot.SIDEBAR);

		map.put(Team.BLUE, board2);

		boardList.put(arena, map);
		// board 2 end

		for (Object s : arena.getPlayers().keySet().toArray()) {
			Player p = Bukkit.getPlayerExact((String) s);

			if (p != null)
				p.setScoreboard(getScoreboard(arena, arena.getTeam(p)));
		}

		updateScoreboard(arena, Team.BLUE, 600);
		updateScoreboard(arena, Team.RED, 600);
	}

	public synchronized void removeScoreboard(Arena arena) {
		for (Object s : arena.getPlayers().keySet().toArray()) {
			Bukkit.getPlayerExact((String) s).setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}

		boardList.remove(arena);
	}

	private Scoreboard getScoreboard(Arena arena, Team t) {
		if (boardList.get(arena).get(t) != null)
			return boardList.get(arena).get(t);
		return null;
	}
	
	public void updateScoreboard(Arena arena, Team t, int timeLeft) {
		Scoreboard board = getScoreboard(arena, t);

		if (board != null) {
			board.getObjective(t.toString().toLowerCase()).unregister();

			Objective o = board.registerNewObjective(t.toString().toLowerCase(), "dummy");
			o.setDisplayName((t == Team.RED ? ChatColor.RED : ChatColor.BLUE) + t.toString().substring(0, 1).toUpperCase() + t.toString().substring(1).toLowerCase() + " Team");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);

			Score s = o.getScore(Bukkit.getOfflinePlayer("Time: " + TimeUtil.formatIntoHHMMSS(timeLeft)));
			s.setScore(2);

			Score s2 = o.getScore(Bukkit.getOfflinePlayer("Score: " + ScoreManager.getInstance().getScore(arena, t)));
			s2.setScore(1);
		}
	}
}

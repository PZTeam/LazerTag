package me.assist.lazertag.managers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.assist.lazertag.LazerTag;
import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.Team;
import me.assist.lazertag.player.PlayerFile;
import me.assist.lazertag.util.ValueComparator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerManager {

	private HashMap<String, Integer> kills = new HashMap<String, Integer>();
	private HashMap<String, Integer> deaths = new HashMap<String, Integer>();

	private static PlayerManager playerStatManager;

	public static PlayerManager getInstance() {
		if (playerStatManager == null)
			playerStatManager = new PlayerManager();
		return playerStatManager;
	}

	public void setupStat(Player player) {
		kills.put(player.getName(), 0);
		deaths.put(player.getName(), 0);
	}

	public boolean isSetup(String name) {
		return kills.containsKey(name) && deaths.containsKey(name);
	}

	public void addKill(Player p) {
		if (!isSetup(p.getName()))
			setupStat(p);

		int cur = kills.containsKey(p.getName()) ? kills.get(p.getName()) : 0;
		kills.put(p.getName(), ++cur);
	}

	public void addDeath(Player player) {
		if (!isSetup(player.getName()))
			setupStat(player);
		int cur = deaths.get(player.getName());

		deaths.remove(player.getName());
		deaths.put(player.getName(), cur++);
	}

	public void addDoublePoints(String name, int amount) {
		PlayerFile file = new PlayerFile(name);

		file.getConfig().set("doublePoints", getDoublePoints(name) + amount);
		file.save();
	}

	public int getKills(String name) {
		return new PlayerFile(name).getConfig().getInt("kills", 0);
	}

	public int getDeaths(String name) {
		return new PlayerFile(name).getConfig().getInt("deaths", 0);
	}

	public int getPoints(String name) {
		return new PlayerFile(name).getConfig().getInt("points", 0);
	}

	public int getDoublePoints(String name) {
		return new PlayerFile(name).getConfig().getInt("doublePoints", 0);
	}

	public void removePoints(String name, int amount) {
		PlayerFile file = new PlayerFile(name);
		int points = file.getConfig().getInt("points");

		file.getConfig().set("points", points - amount < 0 ? 0 : points - amount);
		file.save();
	}

	public Map<String, Integer> topKills() {
		HashMap<String, Integer> top = new HashMap<String, Integer>();

		for (File f : new File(LazerTag.getInstance().getDataFolder() + File.separator + "stats").listFiles()) {
			String realName = f.getName().replaceAll(".yml", "");
			top.put(realName, getKills(realName));
		}

		return ValueComparator.sortByValues(top);
	}

	public Map<String, Integer> topDeaths() {
		HashMap<String, Integer> top = new HashMap<String, Integer>();

		for (File f : new File(LazerTag.getInstance().getDataFolder() + File.separator + "stats").listFiles()) {
			String realName = f.getName().replaceAll(".yml", "");
			top.put(realName, getDeaths(realName));
		}

		return ValueComparator.sortByValues(top);
	}

	public void save1(Arena arena, Team t, boolean win) {
		for (Object s : arena.getPlayers().keySet().toArray()) {
			String name = (String) s;
			Player p = Bukkit.getPlayerExact(name);

			if (p != null) {
				if (arena.getTeam(p) == t) {
					if (!isSetup(name))
						setupStat(p);

					PlayerFile stat = new PlayerFile(name);
					int points = getDoublePoints(name);
					
					if (points > 0) {
						stat.getConfig().set("points", stat.getConfig().getInt("points", 0) + (kills.get(name) + 10 + (win ? 10 : 0) * 2));
						stat.getConfig().set("doublePoints", points == 1 ? 0 : points - 1);
					} else {
						stat.getConfig().set("points", stat.getConfig().getInt("points", 0) + kills.get(name) + 10 + (win ? 10 : 0));
					}

					stat.save();
				}
			}
		}

		save2();
	}

	public void save1_2(String name) {
		if (!isSetup(name))
			return;

		PlayerFile stat = new PlayerFile(name);
		int points = getDoublePoints(name);
		
		if (points > 0) {
			stat.getConfig().set("points", stat.getConfig().getInt("points", 0) + (kills.get(name) * 2));
			stat.getConfig().set("doublePoints", points == 1 ? 0 : points - 1);
		} else {
			stat.getConfig().set("points", stat.getConfig().getInt("points", 0) + kills.get(name));
		}

		stat.save();
		save2_2(name);
	}

	public void save2() {
		for (String s : kills.keySet()) {
			save2_2(s);
		}
	}

	public void save2_2(String name) {
		if (!isSetup(name))
			return;

		PlayerFile stat = new PlayerFile(name);

		stat.getConfig().set("kills", stat.getConfig().getInt("kills", 0) + kills.get(name));
		stat.getConfig().set("deaths", stat.getConfig().getInt("deaths", 0) + deaths.get(name));

		stat.save();

		kills.remove(name);
		deaths.remove(name);
	}
}

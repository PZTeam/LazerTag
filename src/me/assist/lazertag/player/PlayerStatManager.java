package me.assist.lazertag.player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.assist.lazertag.LazerTag;
import me.assist.lazertag.util.ValueComparator;

import org.bukkit.entity.Player;

public class PlayerStatManager {

	private HashMap<String, Integer> kills = new HashMap<String, Integer>();
	private HashMap<String, Integer> deaths = new HashMap<String, Integer>();

	private static PlayerStatManager playerStatManager;

	public static PlayerStatManager getInstance() {
		if (playerStatManager == null)
			playerStatManager = new PlayerStatManager();
		return playerStatManager;
	}

	public void setupStat(Player player) {
		kills.put(player.getName(), 0);
		deaths.put(player.getName(), 0);
	}

	public boolean isSetup(String name) {
		return kills.containsKey(name) && deaths.containsKey(name);
	}

	public void addKill(Player player) {
		if (!isSetup(player.getName()))
			setupStat(player);

		int cur = kills.get(player.getName());

		kills.remove(player.getName());
		kills.put(player.getName(), cur++);
	}

	public void addDeath(Player player) {
		if (!isSetup(player.getName()))
			setupStat(player);
		int cur = deaths.get(player.getName());

		deaths.remove(player.getName());
		deaths.put(player.getName(), cur++);
	}

	public int getKills(String name) {
		return new PlayerStat(name).getConfig().getInt("kills", 0);
	}

	public int getDeaths(String name) {
		return new PlayerStat(name).getConfig().getInt("deaths", 0);
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

	public void saveData() {
		for (String s : kills.keySet()) {
			saveSpecific(s);
		}
	}

	public void saveSpecific(String name) {
		if (!isSetup(name))
			return;

		PlayerStat stat = new PlayerStat(name);

		stat.getConfig().set("kills", stat.getConfig().getInt("kills", 0) + kills.get(name));
		stat.getConfig().set("deaths", stat.getConfig().getInt("deaths", 0) + deaths.get(name));

		stat.save();

		kills.remove(name);
		deaths.remove(name);
	}
}

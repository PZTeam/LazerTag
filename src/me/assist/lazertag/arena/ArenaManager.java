package me.assist.lazertag.arena;

import java.util.ArrayList;

public class ArenaManager {

	private ArrayList<Arena> arenas;
	private static ArenaManager arenaManager;

	public static ArenaManager getInstance() {
		if (arenaManager == null)
			arenaManager = new ArenaManager();
		return arenaManager;
	}

	public Arena createArena(String id) {
		Arena arena = new Arena(id);
		getArenas().add(arena);

		ArenaFile file = new ArenaFile(id);
		file.load();

		if (!file.getConfig().contains("minPlayers")) {
			file.getConfig().set("minPlayers", 8);
		}
		
		if (!file.getConfig().contains("maxPlayers")) {
			file.getConfig().set("maxPlayers", 10);
		}
		
		if (!file.getConfig().contains("scoreLimit")) {
			file.getConfig().set("scoreLimit", 50);
		}
		
		file.save();
		return arena;
	}

	public void deleteArena(String id) {
		arenas.remove(getArena(id));

		ArenaFile file = new ArenaFile(id);
		file.delete();
	}

	public ArrayList<Arena> getArenas() {
		if (arenas == null)
			arenas = new ArrayList<Arena>();
		return arenas;
	}

	public Arena getArena(String id) {
		for (Arena a : getArenas()) {
			if (a.getName().equals(id)) {
				return a;
			}
		}

		return null;
	}
}

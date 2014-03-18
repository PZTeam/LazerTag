package me.assist.lazertag;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaFile;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.arena.ArenaState;
import me.assist.lazertag.util.LocationUtil;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SignWall {

	public static boolean updateSign(Sign sign) {
		String[] lines = sign.getLines();

		if (!lines[2].equals("") || !lines[2].equals(" ")) {
			Arena arena = ArenaManager.getInstance().getArena(lines[2]);

			if (arena != null) {
				String state = lines[1];
				ArenaState arenaState = arena.getState();

				if (arenaState == ArenaState.WAITING && !state.equals("Click to Join")) {
					sign.setLine(1, "Click to Join");
				}

				if (arenaState == ArenaState.PLAYING && !state.equals("Game Running")) {
					sign.setLine(1, "Game Running");
				}

				int maxPlayers = arena.getMaxPlayers();
				int players = Integer.parseInt(lines[3].split("/")[0]);
				int arenaPlayers = arena.getPlayers().size();

				if (arenaPlayers != players || Integer.parseInt(lines[3].split("/")[1]) != maxPlayers) {
					sign.setLine(3, arenaPlayers + "/" + maxPlayers);
				}

				sign.update();
				return true;
			}
		}

		return false;
	}

	public static void updateSigns(Arena arena) {
		ArenaFile file = new ArenaFile(arena.getName());

		if (file.getConfig().contains("signs") && file.getConfig().isConfigurationSection("signs")) {
			for (String s : file.getConfig().getConfigurationSection("signs").getKeys(false)) {
				String str = file.getConfig().getString("signs." + s + ".location");
				Location loc = LocationUtil.stringToLocation(str);

				if (loc != null) {
					Block b = loc.getBlock();

					if (b.getState() instanceof Sign) {
						Sign sign = (Sign) b.getState();
						updateSign(sign);
					}
				}
			}
		}
	}
}

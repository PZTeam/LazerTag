package me.assist.lazertag.util;

import java.util.HashSet;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaFile;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.arena.Team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class LocationUtil {

	public static Location getSpawn(Arena arena, Team t) {
		ArenaFile file = new ArenaFile(arena.getName());

		if (!file.getConfig().contains(t.toString().toLowerCase() + "spawn"))
			return null;

		return stringToLocation(file.getConfig().getString(t.toString().toLowerCase() + "spawn"));
	}

	public static void setSpawn(Arena arena, Team t, Location loc) {
		ArenaFile file = new ArenaFile(arena.getName());

		file.getConfig().set(t.toString().toLowerCase() + "spawn", locationToString(loc, false));
		file.save();
	}

	public static boolean isLobbySign(Sign sign) {
		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			if (arena.getFile().getConfig().contains("signs")) {
				for (String key : arena.getFile().getConfig().getConfigurationSection("signs").getKeys(false)) {
					if (sign.getLocation().equals(stringToLocation(arena.getFile().getConfig().getString("signs." + key + ".location")))) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean isInCuboid(Player player, Block start, Block end) {
		return new Cuboid(start.getLocation(), end.getLocation()).contains(player.getLocation());
	}

	public static Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<Entity> radiusEntities = new HashSet<Entity>();

		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();

				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock())
						radiusEntities.add(e);
				}
			}
		}

		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

	public static Location stringToLocation(String str) {
		String[] split = str.split(":");

		if (split.length == 6) {
			World world = Bukkit.getWorld(split[0]);

			double x = Double.parseDouble(split[1]);
			double y = Double.parseDouble(split[2]);
			double z = Double.parseDouble(split[3]);

			float yaw = Float.parseFloat(split[4]);
			float pitch = Float.parseFloat(split[5]);

			return new Location(world, x, y, z, yaw, pitch);
		}

		return null;
	}

	public static String locationToString(Location loc, boolean block) {
		return loc.getWorld().getName() + ":" + (block ? loc.getBlockX() : loc.getX()) + ":" + (block ? loc.getBlockY() : loc.getY()) + ":" + (block ? loc.getBlockZ() : loc.getZ()) + ":" + loc.getYaw() + ":" + loc.getPitch();
	}
}

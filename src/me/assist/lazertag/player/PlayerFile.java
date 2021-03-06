package me.assist.lazertag.player;

import java.io.File;
import java.io.IOException;

import me.assist.lazertag.LazerTag;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerFile {

	private String name = null;

	private File file;
	private FileConfiguration conf;

	public PlayerFile(String name) {
		this.name = name;
	}

	public void load() {
		if (file == null) {
			file = new File(LazerTag.getInstance().getDataFolder() + File.separator + "players", name + ".yml");
		}

		conf = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration getConfig() {
		if (conf == null) {
			load();
		}

		return conf;
	}

	public void save() {
		if (file == null || conf == null) {
			return;
		}

		try {
			getConfig().save(file);
		} catch (IOException ex) {
			LazerTag.getInstance().getLogger().severe("Unable to save " + name + ".yml.");
			ex.printStackTrace();
		}
	}
}

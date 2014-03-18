package me.assist.lazertag;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import me.assist.lazertag.arena.Arena;
import me.assist.lazertag.arena.ArenaManager;
import me.assist.lazertag.commands.CommandHandler;
import me.assist.lazertag.game.GameManager;
import me.assist.lazertag.game.StopReason;
import me.assist.lazertag.listeners.BlockBreakListener;
import me.assist.lazertag.listeners.EntityDamageByEntityListener;
import me.assist.lazertag.listeners.EntityDamageListener;
import me.assist.lazertag.listeners.EntityDeathListener;
import me.assist.lazertag.listeners.InventoryClickListener;
import me.assist.lazertag.listeners.PlayerChatListener;
import me.assist.lazertag.listeners.PlayerInteractListener;
import me.assist.lazertag.listeners.PlayerJoinListener;
import me.assist.lazertag.listeners.PlayerKickListener;
import me.assist.lazertag.listeners.PlayerMoveListener;
import me.assist.lazertag.listeners.PlayerPickupItemListener;
import me.assist.lazertag.listeners.PlayerQuitListener;
import me.assist.lazertag.listeners.PlayerRespawnListener;
import me.assist.lazertag.listeners.SignChangeListener;
import me.assist.lazertag.player.PlayerStatManager;
import me.assist.lazertag.util.Metrics;
import me.assist.lazertag.util.Updater;
import me.assist.lazertag.util.Updater.ReleaseType;
import me.assist.lazertag.util.Updater.UpdateType;
import me.assist.lazertag.util.WeaponUtil;
import me.assist.lazertag.util.WorldEditUtil;

import org.bukkit.plugin.java.JavaPlugin;

public class LazerTag extends JavaPlugin {

	private String prefix = "";

	private static LazerTag instance;
	private static File file;

	private static boolean update = false;
	private static String name = "";
	private static ReleaseType type = null;
	private static String version = "";
	private static String link = "";

	public int fileId = 0;

	public void onEnable() {
		if (getServer().getPluginManager().getPlugin("WorldEdit") != null) {
			new WorldEditUtil().setupWorldEdit(getServer().getPluginManager());
			getLogger().severe("Succesfully hooked to WorldEdit!");
		} else {
			getLogger().severe("WorldEdit not found - disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		instance = this;
		file = getFile();

		prefix = WeaponUtil.colorize(getConfig().getString("chatPrefix"));

		getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerPickupItemListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
		getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
		getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerKickListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
		getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);

		getCommand("lazertag").setExecutor(new CommandHandler());

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			getLogger().severe("Metrics was unable to submit stats.");
		}

		getConfig().options().copyDefaults(true);
		saveConfig();

		loadExistingArenas();
		checkUpdate();
	}

	public void onDisable() {
		for (Arena arena : ArenaManager.getInstance().getArenas()) {
			arena.reset();

			GameManager.getInstance().stopCountdown(arena);
			GameManager.getInstance().stopGame(arena, StopReason.FORCE, null);
		}

		PlayerStatManager.getInstance().saveData();
	}

	private void checkUpdate() {
		if (getConfig().get("updater.enabled") == null) {
			getConfig().set("updater.enabled", false);
			saveConfig();
		}

		if (getConfig().get("updater.auto-update") == null) {
			getConfig().set("updater.auto-update", false);
			saveConfig();
		}

		if (getConfig().getBoolean("updater.enabled")) {
			if (getConfig().getBoolean("updater.auto-update")) {
				getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
					@Override
					public void run() {
						Updater updater = new Updater(LazerTag.getInstance(), fileId, getFile(), UpdateType.NO_VERSION_CHECK, true);

						if (updater.getResult() == Updater.UpdateResult.SUCCESS) {
							log(Level.INFO, "Successfully updated LazerTag to the latest version.");
						} else {
							log(Level.SEVERE, "Updater was unable to update LazerTag.");
						}

						return;
					}
				});
			}

			Updater updater = new Updater(this, fileId, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
			update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;

			if (update) {
				name = updater.getLatestName();
				version = updater.getLatestGameVersion();
				type = updater.getLatestType();
				link = updater.getLatestFileLink();
			}
		}
	}

	public static LazerTag getInstance() {
		return instance;
	}

	public File getPluginFile() {
		return file;
	}

	public boolean updateAvailable() {
		return update;
	}

	public String getPluginName() {
		return name;
	}

	public ReleaseType getReleaseType() {
		return type;
	}

	public String getVersion() {
		return version;
	}

	public String getLink() {
		return link;
	}

	public String getPrefix() {
		return prefix;
	}

	public void log(java.util.logging.Level level, String... strings) {
		for (String s : strings) {
			getLogger().log(level, s);
		}
	}

	private void loadExistingArenas() {
		if (new File(this.getDataFolder() + File.separator + "arenas").exists()) {
			for (File file : new File(this.getDataFolder() + File.separator + "arenas").listFiles()) {
				String realName = file.getName().replaceAll(".yml", "");
				Arena arena = ArenaManager.getInstance().createArena(realName);

				SignWall.updateSigns(arena);
				log(Level.INFO, "<< Loaded arena '" + realName + "' from data folder! >>");
			}

		} else {
			log(Level.INFO, "No pre-existing arenas were found.");
		}
	}
}

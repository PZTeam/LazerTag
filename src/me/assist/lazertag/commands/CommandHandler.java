package me.assist.lazertag.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import me.assist.lazertag.Header;
import me.assist.lazertag.LazerTag;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class CommandHandler implements CommandExecutor {

	private HashMap<String, SubCommand> commands;

	public CommandHandler() {
		commands = new HashMap<String, SubCommand>();
		loadCommands();
	}

	private void loadCommands() {
		commands.put("createarena", new CreateArena());
		commands.put("deletearena", new DeleteArena());
		commands.put("setspawn", new SetSpawn());
		commands.put("forcestart", new ForceStart());
		commands.put("forcestop", new ForceStop());
		commands.put("join", new Join());
		commands.put("leave", new Leave());
		commands.put("spectate", new Spectate());
		commands.put("list", new List());
		commands.put("top", new Top());
		commands.put("me", new Me());
		commands.put("update", new Update());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			LazerTag.getInstance().getLogger().warning("Only in-game players can use LazerTag commands!");
			return true;
		}

		PluginDescriptionFile pdfFile = LazerTag.getInstance().getDescription();
		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("lazertag")) {
			if (args.length < 1) {
				player.sendMessage(Header.NEUTRAL + "Version " + pdfFile.getVersion() + " by Assist");
				return true;
			}

			String sub = args[0];
			Vector<String> l = new Vector<String>();
			l.addAll(Arrays.asList(args));
			l.remove(0);
			args = (String[]) l.toArray(new String[0]);

			if (!commands.containsKey(sub)) {
				player.sendMessage(Header.NEGATIVE + "Command doesn't exist.");
				return true;
			}

			commands.get(sub).execute(player, args);
			return true;
		}

		return false;
	}
}

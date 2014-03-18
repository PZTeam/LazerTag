package me.assist.lazertag.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.assist.lazertag.BoardManager;
import me.assist.lazertag.SignWall;
import me.assist.lazertag.game.GameManager;
import me.assist.lazertag.game.ScoreManager;
import me.assist.lazertag.player.PlayerData;
import me.assist.lazertag.player.PlayerStatManager;
import me.assist.lazertag.util.LocationUtil;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Arena {

	private HashMap<String, Team> playerList;
	private ArrayList<String> spectatorList;

	private String id = "";
	private ArenaState state;
	private ArenaFile file;

	public Arena(String id) {
		this.id = id;
		this.state = ArenaState.WAITING;

		file = new ArenaFile(getName());
	}

	public void addPlayer(Player player, Team team) {
		getPlayers().put(player.getName(), team);
		PlayerData.getInstance().prepareAddPlayer(player);

		player.teleport(LocationUtil.getSpawn(this, team));
		u();

		if (getPlayers().size() >= getMinPlayers()) {
			GameManager.getInstance().startCountdown(this);
		}
	}

	public void removePlayer(Player player) {
		getPlayers().remove(player.getName());
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		PlayerData.getInstance().prepareRemovePlayer(player);
		u();
	}

	public boolean containsPlayer(Player player) {
		return getPlayers().containsKey(player.getName());
	}

	public void addSpectator(Player player) {
		getSpectators().add(player.getName());
		PlayerData.getInstance().prepareAddPlayer(player);

		for (Object s : getPlayers().keySet().toArray()) {
			Player p = Bukkit.getServer().getPlayerExact((String) s);
			p.hidePlayer(player);
		}

		player.teleport(LocationUtil.getSpawn(this, Team.values()[new Random().nextInt(Team.values().length)]));

		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
		player.setAllowFlight(true);
		player.setFlying(true);
	}

	public void removeSpectator(Player player) {
		getSpectators().remove(player.getName());
		PlayerData.getInstance().prepareRemovePlayer(player);

		for (Object s : getPlayers().keySet().toArray()) {
			Player p = Bukkit.getServer().getPlayerExact((String) s);
			p.showPlayer(player);
		}

		player.setAllowFlight(player.getGameMode() == GameMode.CREATIVE);
		player.setFlying(false);
	}

	public boolean containsSpectator(Player player) {
		return getSpectators().contains(player.getName());
	}

	public boolean isJoinable() {
		return (LocationUtil.getSpawn(this, Team.RED) != null && LocationUtil.getSpawn(this, Team.BLUE) != null) && state != ArenaState.PLAYING && getPlayers().size() < getMaxPlayers();
	}

	public boolean isSpectatable() {
		return state == ArenaState.PLAYING;
	}

	public void broadcastMessage(String message) {
		for (Object string : getPlayers().keySet().toArray()) {
			Player player = Bukkit.getPlayerExact((String) string);

			if (player != null)
				player.sendMessage(message);
			else
				getPlayers().remove(string);
		}
	}

	public Block getStartBlock() {
		return LocationUtil.stringToLocation(file.getConfig().getString("region.start")).getBlock();
	}

	public Block getEndBlock() {
		return LocationUtil.stringToLocation(file.getConfig().getString("region.end")).getBlock();
	}

	public int getMaxPlayers() {
		return file.getConfig().getInt("maxPlayers", 10);
	}

	public int getMinPlayers() {
		return file.getConfig().getInt("minPlayers", 8);
	}

	public Team getTeam(Player player) {
		return containsPlayer(player) ? getPlayers().get(player.getName()) : null;
	}

	public HashMap<String, Team> getPlayers() {
		if (playerList == null)
			playerList = new HashMap<String, Team>();
		return playerList;
	}

	public ArrayList<String> getSpectators() {
		if (spectatorList == null)
			spectatorList = new ArrayList<String>();
		return spectatorList;
	}

	public void setState(ArenaState state) {
		this.state = state;
	}

	public ArenaState getState() {
		return state;
	}

	public String getName() {
		return id;
	}

	public ArenaFile getFile() {
		return file;
	}

	public synchronized void reset() {
		PlayerStatManager.getInstance().saveData();

		ScoreManager.getInstance().resetScores(this);
		BoardManager.getInstance().removeScoreboard(this);
		PlayerStatManager.getInstance().saveData();

		for (Object s : getPlayers().keySet().toArray()) {
			Player p = Bukkit.getPlayerExact((String) s);

			if (p != null)
				removePlayer(Bukkit.getPlayerExact((String) s));
		}
		
		for (Object s : getSpectators().toArray()) {
			Player p = Bukkit.getPlayerExact((String) s);
			
			if(p != null)
				removeSpectator(Bukkit.getPlayerExact((String) s));
		}

		state = ArenaState.WAITING;
		u();
	}

	public void u() {
		SignWall.updateSigns(this);
	}
}

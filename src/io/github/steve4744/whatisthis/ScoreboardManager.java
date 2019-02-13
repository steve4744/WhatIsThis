package io.github.steve4744.whatisthis;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import io.github.steve4744.whatisthis.data.DataHandler;

public class ScoreboardManager {
	private Scoreboard scoreboard;
	private WhatIsThis plugin;
	private DataHandler dataHandler;
	
	private HashMap<String, Scoreboard> scoreboardMap = new HashMap<String, Scoreboard>();
	private HashMap<String, BukkitTask> taskMap = new HashMap<String, BukkitTask>();

	public ScoreboardManager(WhatIsThis plugin) {
		this.plugin = plugin;
		this.dataHandler = plugin.getDataHandler();
	}

	public void showTarget(Player player, Block block) {
		//kill any previous scheduled tasks
		cancelTask(player);

		if (scoreboardMap.containsKey(player.getName())) {
			scoreboard = scoreboardMap.get(player.getName());
		} else {
			scoreboard = buildScoreboard();
			scoreboardMap.put(player.getName(), scoreboard);
		}
		//remove previous entry
		resetScoreboard(player);

		Objective o = scoreboard.getObjective(DisplaySlot.SIDEBAR);
		o.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + dataHandler.getDisplayName(block.getType()));

		for (String s : dataHandler.getItemDrops(block)) {
			o.getScore(dataHandler.getFormattedText(block, s)).setScore(dataHandler.getAmount(block, s));
		}
		player.setScoreboard(scoreboard);

		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				resetScoreboard(player);
			}
		}.runTaskLater(plugin, 60);
		taskMap.put(player.getName(), task);
	}

	private Scoreboard buildScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = scoreboard.registerNewObjective("WhatIsThis", "showblock", "WhatIsThis");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		return scoreboard;
	}

	private void resetScoreboard(Player player) {
		scoreboard = scoreboardMap.get(player.getName());
		for (String entry : new ArrayList<String>(scoreboard.getEntries())) {
			scoreboard.resetScores(entry);
		}
	}

	private void cancelTask(Player player) {
		BukkitTask task = null;
		if (taskMap.containsKey(player.getName())) {
			task = taskMap.get(player.getName());
			task.cancel();
			taskMap.remove(player.getName());
		}
	}

}

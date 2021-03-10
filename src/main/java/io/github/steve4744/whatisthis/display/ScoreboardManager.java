/*
 * MIT License

Copyright (c) 2019 steve4744

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */
package io.github.steve4744.whatisthis.display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import io.github.steve4744.whatisthis.WhatIsThis;
import io.github.steve4744.whatisthis.data.DataHandler;

public class ScoreboardManager {
	private Scoreboard scoreboard;
	private WhatIsThis plugin;
	private DataHandler dataHandler;
	
	private Map<String, Scoreboard> scoreboardMap = new HashMap<>();
	private Map<String, BukkitTask> taskMap = new HashMap<>();
	private Map<String, Scoreboard> externalScoreboards = new HashMap<>();

	public ScoreboardManager(WhatIsThis plugin) {
		this.plugin = plugin;
		this.dataHandler = plugin.getDataHandler();
	}

	public void showTarget(Player player, Block block) {
		//kill any previous scheduled tasks
		cancelTask(player.getName());
		storeExternalScoreboard(player);

		if (scoreboardMap.containsKey(player.getName())) {
			scoreboard = scoreboardMap.get(player.getName());
		} else {
			scoreboard = buildScoreboard();
			scoreboardMap.put(player.getName(), scoreboard);
		}
		//remove previous entry
		resetScoreboard(player);

		Objective o = scoreboard.getObjective(DisplaySlot.SIDEBAR);
		o.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + dataHandler.getDisplayName(block, player));

		for (String s : dataHandler.getItemDrops(block)) {
			o.getScore(dataHandler.getFormattedText(block, s, player)).setScore(dataHandler.getAmount(block, s));
		}
		player.setScoreboard(scoreboard);

		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				resetScoreboard(player);
				restoreExternalScoreboard(player);
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

	private void cancelTask(String playername) {
		if (taskMap.containsKey(playername)) {
			BukkitTask task = taskMap.get(playername);
			task.cancel();
			taskMap.remove(playername);
		}
	}

	/**
	 * Store 3rd party scoreboard if the scoreboard displayed is not of this plugin,
	 * i.e. ignore if player is multi-clicking items.
	 *
	 * @param player
	 */
	private void storeExternalScoreboard(Player player) {
		if (externalScoreboards.get(player.getName()) == null) {
			externalScoreboards.put(player.getName(), player.getScoreboard());
		}
	}

	/**
	 * Restore player's 3rd party scoreboard, if previously saved.
	 *
	 * @param player
	 */
	private void restoreExternalScoreboard(Player player) {
		if (externalScoreboards.get(player.getName()) != null) {
			player.setScoreboard(externalScoreboards.remove(player.getName()));
		}
	}
}

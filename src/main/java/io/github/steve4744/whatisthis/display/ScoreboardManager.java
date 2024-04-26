/*
 * MIT License

Copyright (c) 2023 steve4744

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
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import io.github.steve4744.whatisthis.WhatIsThis;
import io.github.steve4744.whatisthis.data.DataHandler;
import io.github.steve4744.whatisthis.data.ItemDropRanges;

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

	public void showTarget(Player player, String message, String prefix, Block block) {
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

		if (message.isEmpty()) {
			restoreExternalScoreboard(player);
			return;
		}

		Objective o = scoreboard.getObjective(DisplaySlot.SIDEBAR);
		o.setDisplayName(prefix + ChatColor.GOLD.toString() + ChatColor.BOLD + message);

		if (block != null) {
			for (String s : dataHandler.getItemDrops(block, player)) {
				o.getScore(getFormattedText(block, s, player)).setScore(getMaxDrops(block, s));
			}
		} else {
			o.getScore(getText() + " : Not Supported").setScore(0);
		}
		player.setScoreboard(scoreboard);

		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				resetScoreboard(player);
				restoreExternalScoreboard(player);
			}
		}.runTaskLater(plugin, plugin.getSettings().getScoreboardTimeout());
		taskMap.put(player.getName(), task);
	}

	private Scoreboard buildScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = scoreboard.registerNewObjective("WhatIsThis", Criteria.DUMMY, "WhatIsThis");
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

	/**
	 * Format the text for showing the items that the block can drop.
	 * Translate this into the appropriate language.
	 * Truncate if > 40 chars for scoreboard.
	 *
	 * @param block
	 * @param item
	 * @param player
	 * @return translated string
	 */
	private String getFormattedText(Block block, String item, Player player) {
		String result = getText() + " : " + ChatColor.RED;
		if (item.isEmpty()) {
			return result;
		}

		String range = getRange(block, item);
		String separator = range != null ? " " + range.substring(0, range.length() -2) : "  x";

		// Scoreboard max length is 40, so subtract length of result and separator
		int maxlen = 40 - result.length() - separator.length();
		String translated = dataHandler.isCustomBlock(block) ? item : dataHandler.translateItemName(item, player);

		return result + translated.substring(0, Math.min(translated.length(), maxlen)) + separator;
	}

	private String getRange(Block block, String item) {
		if (dataHandler.isCustomBlock(block) || !dataHandler.hasDropRange(block)) {
			return null;
		}
		Map<String, String> itemDrops = ItemDropRanges.valueOf(block.getType().toString()).getMap();
		return itemDrops.get(item);
	}

	private int getMaxDrops(Block block, String item) {
		if (dataHandler.isCustomBlock(block) || !dataHandler.hasDropRange(block)) {
			return dataHandler.getFixedAmount(item);
		}
		String range = getRange(block, item);
		return Integer.valueOf(range.substring(range.length() -1));
	}

	/**
	 * Get the text to display the 'Drops'. Can be translated to suit server language.
	 *
	 * @return text for 'Drops'
	 */
	private String getText() {
		return plugin.getConfig().getString("text.drops", "Drops");
	}
}

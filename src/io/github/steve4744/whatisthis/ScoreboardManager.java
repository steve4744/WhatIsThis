package io.github.steve4744.whatisthis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;


public class ScoreboardManager {
	private Scoreboard scoreboard;
	private WhatIsThis plugin;
	
	private HashMap<String, Scoreboard> scoreboardMap = new HashMap<String, Scoreboard>();
	private HashMap<String, BukkitTask> taskMap = new HashMap<String, BukkitTask>();

	public ScoreboardManager(WhatIsThis plugin) {
		this.plugin = plugin;
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
		o.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + getDisplayName(block.getType()));
		for (String aa : getDrops(block)) {
			o.getScore(getFormattedText(aa)).setScore(block.getDrops().size());
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

	private String getDisplayName(Material target) {
		return target.toString();
	}

	private String getText() {
		//FileConfiguration cfg = Configuration.getStringData();
		//return cfg.getString("text.growth", "Growth");
		return "Drops";
	}

	private List<String> getDrops(Block block) {
		List<String> itemDrops = new ArrayList<String>();
		Collection<ItemStack> coll = new ArrayList<ItemStack>();
		coll = block.getDrops();
		if (coll.isEmpty()) {
			itemDrops.add("");
			return itemDrops;
		}
		//TODO some crops drop 2 different itemstacks, so getting the first isn't reliable. block#getDrops doesn't return these correctly.
		ItemStack item = coll.iterator().next();
		itemDrops.add(item.getType().toString());

		return itemDrops;
	}
	
	private String getFormattedText(String item) {
		String result = getText() + " : ";
		// result has to be <=40 char limit for SB
		if (item.equalsIgnoreCase("HEAVY_WEIGHTED_PRESSURE_PLATE")) {
			item = "IRON_PRESSURE_PLATE";
		} else if (item.equalsIgnoreCase("LIGHT_WEIGHTED_PRESSURE_PLATE")) {
			item = "GOLD_PRESSURE_PLATE";
		}
		
		//return getText() + " : " + ChatColor.RED + item + "  x";
		return item.isEmpty() ? result : result + ChatColor.RED + item + "  x";
	}

}

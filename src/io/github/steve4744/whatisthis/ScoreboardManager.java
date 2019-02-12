package io.github.steve4744.whatisthis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
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

import io.github.steve4744.whatisthis.data.InconsistentDropItems;


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

		for (String s : getItemDrops(block)) {
			o.getScore(getFormattedText(block, s)).setScore(getAmount(block, s));
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
		//return cfg.getString("text.drops", "Drops");
		return "Drops";
	}

	private List<String> getItemDrops(Block block) {
		List<String> itemDrops = new ArrayList<String>();
		Collection<ItemStack> coll = new ArrayList<ItemStack>();
		coll = block.getDrops();

		// collection can be empty, e.g. GLASS drops nothing. It can also be empty if the
		// block sometimes drops zero items, or if there is a bug (e.g. Bug: Spigot-1478)
		if (coll.isEmpty() || dropsAreInconsistent(block)) {
			String name = block.getType().toString();
			if (name.equalsIgnoreCase("NETHER_WART")) {
				itemDrops.add(name);

			} else if (name.equalsIgnoreCase("CHORUS_PLANT")) {
				itemDrops.add("CHORUS_FRUIT");

			} else if (name.equalsIgnoreCase("CHORUS_FLOWER")) {
				itemDrops.add(name);

			} else if (name.equalsIgnoreCase("DEAD_BUSH")) {
				itemDrops.add("STICK");

			} else if (name.equalsIgnoreCase("GRASS") || name.equalsIgnoreCase("TALL_GRASS") || name.equalsIgnoreCase("FERN") || name.equalsIgnoreCase("LARGE_FERN")) {
				itemDrops.add("WHEAT_SEEDS");

			} else if (name.equalsIgnoreCase("COCOA")) {
				itemDrops.add("COCOA_BEANS");

			} else if (name.equalsIgnoreCase("RED_MUSHROOM_BLOCK")) {
				itemDrops.add("RED_MUSHROOM");

			} else if (name.equalsIgnoreCase("BROWN_MUSHROOM_BLOCK")) {
				itemDrops.add("BROWN_MUSHROOM");

			} else if (name.equalsIgnoreCase("BEETROOTS")) {
				itemDrops.add("BEETROOTS");
				itemDrops.add("BEETROOT_SEEDS");

			} else if (name.equalsIgnoreCase("WHEAT")) {
				itemDrops.add(0, "WHEAT");
				itemDrops.add(1, "WHEAT_SEEDS");

			} else {
				// items like GLASS drop nothing
				itemDrops.add("");
			}
			return itemDrops;
		}
		//TODO check 1.14 for blocks with >1 unique item drop.
		// get first itemstack (in 1.13 this will be unique as variable drops are listed above).
		ItemStack item = coll.iterator().next();
		itemDrops.add(item.getType().toString());
		return itemDrops;
	}

	private String getFormattedText(Block block, String item) {
		String separator = "  x";
		String result = getText() + " : ";
		// result has to be <=40 char limit for SB
		if (item.equalsIgnoreCase("HEAVY_WEIGHTED_PRESSURE_PLATE")) {
			item = "IRON_PRESSURE_PLATE";

		} else if (item.equalsIgnoreCase("LIGHT_WEIGHTED_PRESSURE_PLATE")) {
			item = "GOLD_PRESSURE_PLATE";
		}
		//define range of variable drops
		if (item.equalsIgnoreCase("NETHER_WART")) {
			separator = ChatColor.WHITE + " # " + ChatColor.RED + "1  " + ChatColor.WHITE + "->" + ChatColor.RED;

		} else if (item.equalsIgnoreCase("CHORUS_FRUIT") || item.equalsIgnoreCase("WHEAT_SEEDS") || item.equalsIgnoreCase("BEETROOT_SEEDS") || item.equalsIgnoreCase("STICK")) {
			separator = ChatColor.WHITE + " # " + ChatColor.RED + "0  " + ChatColor.WHITE + "->" + ChatColor.RED;

		} else if (item.equalsIgnoreCase("RED_MUSHROOM") || item.equalsIgnoreCase("BROWN_MUSHROOM")) {
			if (block.getType().toString().contains("MUSHROOM_BLOCK")) {
				separator = ChatColor.WHITE + " # " + ChatColor.RED + "0  " + ChatColor.WHITE + "->" + ChatColor.RED;;
			}
		}

		return item.isEmpty() ? result : result + ChatColor.RED + item + separator;
	}

	private boolean dropsAreInconsistent(Block block) { 
		return EnumUtils.isValidEnum(InconsistentDropItems.class, block.getType().toString());
	}

	private int getAmount(Block block, String name) {
		if (!dropsAreInconsistent(block)) {
			return block.getDrops().size();
		}
		int amount = 0;
		switch (block.getType().toString()) {
			case "NETHER_WART":
				amount = 4;
				break;
			case "COCOA":
				amount = 3;
				break;
			case "WHEAT":
			case "BEETROOTS":
				if (name.contains("SEEDS")) {
					amount = 3;
				} else {
					amount = 1;
				}
				break;
			case "DEAD_BUSH":
			case "RED_MUSHROOM_BLOCK":
			case "BROWN_MUSHROOM_BLOCK":
				amount = 2;
				break;
			default: amount = 1;
		}
		return amount;
	}
}

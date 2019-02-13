package io.github.steve4744.whatisthis.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.steve4744.whatisthis.WhatIsThis;

public class DataHandler {

	private WhatIsThis plugin;

	public DataHandler(WhatIsThis plugin) {
		this.plugin = plugin;
	}

	public String getDisplayName(Material target) {
		return target.toString();
	}

	private String getText() {
		//FileConfiguration cfg = Configuration.getStringData();
		//return cfg.getString("text.drops", "Drops");
		return "Drops";
	}

	public List<String> getItemDrops(Block block) {
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

	public String getFormattedText(Block block, String item) {
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

	public int getAmount(Block block, String name) {
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

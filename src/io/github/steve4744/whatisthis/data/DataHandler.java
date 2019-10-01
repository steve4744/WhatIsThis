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
package io.github.steve4744.whatisthis.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Enums;

import io.github.steve4744.whatisthis.Utils;
import io.github.steve4744.whatisthis.WhatIsThis;
import io.github.steve4744.whatisthis.lang.EnumLang;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

public class DataHandler {

	private WhatIsThis plugin;
	private boolean slimefun;
	private Map<String, Integer> itemDrops = new HashMap<>();  // material -> amount

	public DataHandler(WhatIsThis plugin) {
		this.plugin = plugin;
		this.slimefun = plugin.getServer().getPluginManager().isPluginEnabled("Slimefun");
	}

	/**
	 * Get the localised material name. Cater for vanilla issues with WALL_ items.
	 * @param target material name
	 * @param player
	 * @return localised material name
	 */
	public String getDisplayName(Block b, Player player) {
		String targetName = b.getType().toString();
		
		//coloured wall_banners are not currently in the Mojang language files
		if (targetName.contains("WALL_BANNER")) {
			targetName = targetName.replace("WALL_", "");
		}
		
		// Slimefun Support
		if (slimefun) {
			SlimefunItem item = BlockStorage.check(b.getLocation());
			if (item != null) {
				return ChatColor.stripColor(item.getItem().getItemMeta().getDisplayName());
			}
		}
		
		return translateItemName(targetName, player);
	}

	/**
	 * Get the text to display the 'Drops'. Can be translated to suit server language.
	 * @return text for 'Drops'
	 */
	private String getText() {
		return plugin.getConfig().getString("text.drops", "Drops");
	}

	/**
	 * Get the names of the items that can be dropped by the block.
	 * Blocks that can sometimes drop zero items, like LEAVES, are dealt with separately so that
	 * the range of drops can be displayed.
	 * @param block
	 * @param player
	 * @return names of all the items that the block is capable of dropping
	 */
	public Set<String> getItemDrops(Block block, Player player) {
		Set<String> zeroDropItems = new HashSet<String>();

		itemDrops.clear();
		Collection<ItemStack> coll = new ArrayList<ItemStack>();
		coll = block.getDrops();

		if (coll.isEmpty() || dropsAreInconsistent(block)) {
			String name = block.getType().toString();

			if (name.equalsIgnoreCase("DEAD_BUSH")) {
				zeroDropItems.add("STICK");

			} else if (name.equalsIgnoreCase("CHORUS_PLANT")) {
				zeroDropItems.add("CHORUS_FRUIT");

			} else if (name.equalsIgnoreCase("GRASS") || name.equalsIgnoreCase("TALL_GRASS") || name.equalsIgnoreCase("FERN")) {
				zeroDropItems.add("WHEAT_SEEDS");

			} else if (name.equalsIgnoreCase("RED_MUSHROOM_BLOCK")) {
				zeroDropItems.add("RED_MUSHROOM");

			} else if (name.equalsIgnoreCase("BROWN_MUSHROOM_BLOCK")) {
				zeroDropItems.add("BROWN_MUSHROOM");

			} else if (name.equalsIgnoreCase("MELON_STEM")) {
				zeroDropItems.add("MELON_SEEDS");

			} else if (name.equalsIgnoreCase("PUMPKIN_STEM")) {
				zeroDropItems.add("PUMPKIN_SEEDS");

			} else if (name.equalsIgnoreCase("ACACIA_LEAVES")) {
				zeroDropItems.add("ACACIA_SAPLING");

			} else if (name.equalsIgnoreCase("BIRCH_LEAVES")) {
				zeroDropItems.add("BIRCH_SAPLING");

			} else if (name.equalsIgnoreCase("DARK_OAK_LEAVES")) {
				zeroDropItems.add("DARK_OAK_SAPLING");
				zeroDropItems.add("APPLE");

			} else if (name.equalsIgnoreCase("JUNGLE_LEAVES")) {
				zeroDropItems.add("JUNGLE_SAPLING");

			} else if (name.equalsIgnoreCase("OAK_LEAVES")) {
				zeroDropItems.add("OAK_SAPLING");
				zeroDropItems.add("APPLE");

			} else if (name.equalsIgnoreCase("SPRUCE_LEAVES")) {
				zeroDropItems.add("SPRUCE_SAPLING");

			} else if (name.equalsIgnoreCase("CHORUS_FLOWER")) {
				zeroDropItems.add(name);

			// these below are 1.13.only
			} else if (name.equalsIgnoreCase("COCOA")) {
				zeroDropItems.add("COCOA_BEANS");

			} else if (name.equalsIgnoreCase("NETHER_WART")) {
				zeroDropItems.add(name);

			} else if (name.equalsIgnoreCase("BEETROOTS")) {
				zeroDropItems.add(name);
				zeroDropItems.add("BEETROOT_SEEDS");

			} else if (name.equalsIgnoreCase("WHEAT")) {
				zeroDropItems.add(name);
				zeroDropItems.add("WHEAT_SEEDS");

			} else {
				// items like GLASS drop nothing
				zeroDropItems.add("");
			}
			if (name.contains("LEAVES") && !Utils.isMC1_13()) {
				zeroDropItems.add("STICK");
			}

			return zeroDropItems;
		}

		for (ItemStack item : coll) {
			int value = item.getAmount();
			if (itemDrops.containsKey(item.getType().toString())) {
				value += itemDrops.get(item.getType().toString());
			}
			itemDrops.put(item.getType().toString(), value);
		}
		return getItemDropNames();
	}

	/**
	 * Translate the material to its localised name. If not present revert to "en_us".
	 * @param item
	 * @param player
	 * @return localised item name
	 */
	private String translateItemName(String item, Player player) {
		String locale = Utils.getLocale(player);
		String translated = null;

		if (EnumLang.get(locale).getMap().containsKey("item.minecraft." + item.toLowerCase())) {
			translated = EnumLang.get(locale).getMap().get("item.minecraft." + item.toLowerCase());

		} else if (EnumLang.get(locale).getMap().containsKey("block.minecraft." + item.toLowerCase())) {
			translated = EnumLang.get(locale).getMap().get("block.minecraft." + item.toLowerCase());

		} else if (EnumLang.get("en_us").getMap().containsKey("item.minecraft." + item.toLowerCase())) {
			translated = EnumLang.get("en_us").getMap().get("item.minecraft." + item.toLowerCase());

		} else if (EnumLang.get("en_us").getMap().containsKey("block.minecraft." + item.toLowerCase())) {
			translated = EnumLang.get("en_us").getMap().get("block.minecraft." + item.toLowerCase());
		}

		return translated == null ? "not found" : translated;
	}

	/**
	 * Format the text for showing the items that the block can drop. 
	 * Translate this into the appropriate language.
	 * Truncate if > 40 chars for scoreboard.
	 * @param block
	 * @param item
	 * @param player
	 * @return translated string
	 */
	public String getFormattedText(Block block, String item, Player player) {
		String separator = "  x";
		String result = getText() + " : " + ChatColor.RED;
		if (item.isEmpty()) {
			return result;
		}

		//define range of zero drop items
		if (item.equalsIgnoreCase("CHORUS_FRUIT") || item.equalsIgnoreCase("STICK") || item.equalsIgnoreCase("MELON_SEEDS") || item.equalsIgnoreCase("PUMPKIN_SEEDS")) {
			separator = " # 0  ->";

		} else if (item.equalsIgnoreCase("APPLE") || item.contains("SAPLING")) {
			if (block.getType().toString().contains("LEAVES")) {
				separator = " # 0  ->";
			}

		} else if (item.equalsIgnoreCase("WHEAT_SEEDS") && block.getType() != Material.WHEAT) {
			separator = " # 0  ->";

		} else if (item.equalsIgnoreCase("RED_MUSHROOM") || item.equalsIgnoreCase("BROWN_MUSHROOM")) {
			if (block.getType().toString().contains("MUSHROOM_BLOCK")) {
				separator = " # 0  ->";
			}
		}
		if (Utils.isMC1_13()) {
			if (item.equalsIgnoreCase("NETHER_WART")) {
				separator = " # 1  ->";
			} else if (item.equalsIgnoreCase("WHEAT_SEEDS") || item.equalsIgnoreCase("BEETROOT_SEEDS")) {
				separator = " # 0  ->";
			}
		}

		// Scoreboard max length is 40, so subtract length of result and separator
		int maxlen = 40 - result.length() - separator.length();

		String translated = translateItemName(item, player);
		return result + translated.substring(0, Math.min(translated.length(), maxlen)) + separator;
	}

	/**
	 * Returns whether or not this block drops a variable amount, including zero, of the item?
	 * Include bugged items like chorus_flower which always drops itself, but getDrops() returns nothing.
	 * @param block
	 * @return boolean
	 */
	private boolean dropsAreInconsistent(Block block) {
		if (Utils.isMC1_13()) {
			return Enums.getIfPresent(InconsistentDropItems1_13.class, block.getType().toString()).orNull() != null;
		}
		return Enums.getIfPresent(InconsistentDropItems.class, block.getType().toString()).orNull() != null;
	}

	/**
	 * Get the amount of the item that can be dropped. If the item can sometimes drop zero items,
	 * then get the maximum number for that item.
	 * @param block
	 * @param name
	 * @return amount
	 */
	public int getAmount(Block block, String name) {
		if (!dropsAreInconsistent(block)) {
			return itemDrops.get(name) != null ? itemDrops.get(name) : 0;
		}
		int amount = 0;
		switch (block.getType().toString()) {
		    case "MELON_STEM":
		    case "PUMPKIN_STEM":
		    	amount = 3;
		    	break;
			case "RED_MUSHROOM_BLOCK":
			case "BROWN_MUSHROOM_BLOCK":
				amount = 2;
				break;
			// these below are 1.13 only
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

			default: 
				amount = 1;
				if (name.equalsIgnoreCase("STICK")) {
					amount = 2;
				}
		}
		return amount;
	}

	private Set<String> getItemDropNames() {
		return itemDrops.keySet();
	}
}

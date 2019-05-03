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

public class DataHandler {

	private WhatIsThis plugin;
	private Map<String, Integer> newItemDrops = new HashMap<String, Integer>();  // material -> amount

	public DataHandler(WhatIsThis plugin) {
		this.plugin = plugin;
	}

	/**
	 * Get the localised material name. Cater for vanilla issues with WALL_ items.
	 * @param target material name
	 * @param player
	 * @return localised material name
	 */
	public String getDisplayName(Material target, Player player) {
		String targetName = target.toString();
		// wall_sign and coloured wall_banners are not currently in the Mojang language files
		if (targetName.contains("WALL_BANNER") || targetName.contains("WALL_SIGN")) {
			targetName = targetName.replace("WALL_", "");
		}
		return translateItemName(targetName, player);
	}

	private String getText() {
		return plugin.getConfig().getString("text.drops", "Drops");
	}

	public Set<String> getItemDrops(Block block, Player player) {
		Set<String> zeroDropItems = new HashSet<String>();
		
		newItemDrops.clear();
		Collection<ItemStack> coll = new ArrayList<ItemStack>();
		coll = block.getDrops();

		// collection can be empty, e.g. GLASS drops nothing. It can also be empty if the
		// block sometimes drops zero items, or if there is a bug (e.g. Bug: Spigot-1478)
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

			} else {
				// items like GLASS drop nothing
				zeroDropItems.add("");
			}
			if (name.contains("LEAVES")) {
				zeroDropItems.add("STICK");
			}

			return zeroDropItems;
		}

		/* get first itemstack (in 1.13 this will be unique as variable drops are listed above).
		ItemStack item = coll.iterator().next();
		itemDrops.add(item.getType().toString());
		return itemDrops;*/
		
		for (ItemStack item : coll) {
			int value = item.getAmount();
			if (newItemDrops.containsKey(item.getType().toString())) {
				value += newItemDrops.get(item.getType().toString());
			}
			newItemDrops.put(item.getType().toString(), value);
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

		// Scoreboard max length is 40, so subtract length of result and separator
		int maxlen = 40 - result.length() - separator.length();

		String translated = translateItemName(item, player);
		return result + translated.substring(0, Math.min(translated.length(), maxlen)) + separator;
	}

	private boolean dropsAreInconsistent(Block block) { 
		return Enums.getIfPresent(InconsistentDropItems.class, block.getType().toString()).orNull() != null;
	}

	public int getAmount(Block block, String name) {
		if (!dropsAreInconsistent(block)) {
			return newItemDrops.get(name) != null ? newItemDrops.get(name) : 0;
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
			default: 
				amount = 1;
				if (name.equalsIgnoreCase("STICK")) {
					amount = 2;
				}
		}
		return amount;
	}

	private Set<String> getItemDropNames() {
		return newItemDrops.keySet();
	}
}

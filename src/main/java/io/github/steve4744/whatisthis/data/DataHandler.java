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

import io.github.steve4744.whatisthis.WhatIsThis;
import io.github.steve4744.whatisthis.lang.EnumLang;
import io.github.steve4744.whatisthis.utils.OraxenHandler;
import io.github.steve4744.whatisthis.utils.SlimefunHandler;
import io.github.steve4744.whatisthis.utils.Utils;
import io.github.steve4744.whatisthis.utils.CraftoryHandler;
import io.github.steve4744.whatisthis.utils.NovaHandler;
import dev.lone.itemsadder.api.CustomBlock;

public class DataHandler {

	private WhatIsThis plugin;
	private boolean nova;
	private boolean slimefun;
	private boolean itemsadder;
	private boolean oraxen;
	private boolean craftory;
	private Map<String, Integer> itemDrops = new HashMap<>();  // material -> amount

	public DataHandler(WhatIsThis plugin) {
		this.plugin = plugin;
		this.slimefun = plugin.getServer().getPluginManager().isPluginEnabled("Slimefun");
		this.nova = plugin.getServer().getPluginManager().isPluginEnabled("Nova");
		this.itemsadder = plugin.getServer().getPluginManager().isPluginEnabled("ItemsAdder");
		this.oraxen = plugin.getServer().getPluginManager().isPluginEnabled("Oraxen");
		this.craftory = plugin.getServer().getPluginManager().isPluginEnabled("Craftory");
	}

	/**
	 * Get the localised material name. Cater for vanilla issues with WALL_ items.
	 *
	 * @param target material name
	 * @param player
	 * @return localised material name
	 */
	public String getDisplayName(Block block, Player player) {
		if (plugin.getSettings().getBlacklist().contains(block.getType().toString())) {
			return "";
		}
		if (isSlimefunBlock(block)) {
			return ChatColor.stripColor(SlimefunHandler.getSlimefunDisplayName(block));
		}
		if (isNovaBlock(block)) {
			return ChatColor.stripColor(NovaHandler.getBlockName(block.getLocation(), Utils.getLocale(player)));
		}
		if (isItemsAdderBlock(block)) {
			return ChatColor.stripColor(CustomBlock.byAlreadyPlaced(block).getDisplayName());
		}
		if (isOraxenBlock(block)) {
			return ChatColor.stripColor(OraxenHandler.getOraxenDisplayName(block));
		}
		if (isCraftoryBlock(block)) {
			return ChatColor.stripColor(CraftoryHandler.getCraftoryDisplayName(block));
		}

		String targetName = block.getType().toString();
		//coloured wall_banners are not currently in the Mojang language files
		if (targetName.contains("WALL_BANNER")) {
			targetName = targetName.replace("WALL_", "");
		}

		return translateItemName(targetName, player);
	}

	/**
	 * Get the text to display the 'Drops'. Can be translated to suit server language.
	 *
	 * @return text for 'Drops'
	 */
	private String getText() {
		return plugin.getConfig().getString("text.drops", "Drops");
	}

	/**
	 * Get the names of the items that can be dropped by the block.
	 * Blocks that can sometimes drop zero items, like LEAVES, are dealt with separately so that
	 * the range of drops can be displayed.
	 *
	 * @param block
	 * @param player
	 * @return names of all the items that the block is capable of dropping
	 */
	public Set<String> getItemDrops(Block block, Player player) {
		itemDrops.clear();

		if (isSlimefunBlock(block)) {
			itemDrops.put(ChatColor.stripColor(SlimefunHandler.getSlimefunDisplayName(block)), 1);
			return getItemDropNames();
		}
		if (isNovaBlock(block)) {
			itemDrops.put(ChatColor.stripColor(NovaHandler.getBlockName(block.getLocation(), Utils.getLocale(player))), 1);
			return getItemDropNames();
		}
		if (isItemsAdderBlock(block)) {
			itemDrops.put(ChatColor.stripColor(CustomBlock.byAlreadyPlaced(block).getDisplayName()), 1);
			return getItemDropNames();
		}
		if (isOraxenBlock(block)) {
			itemDrops.put(ChatColor.stripColor(OraxenHandler.getOraxenDisplayName(block)), 1);
			return getItemDropNames();
		}
		if (isCraftoryBlock(block)) {
			itemDrops.put(ChatColor.stripColor(CraftoryHandler.getCraftoryDisplayName(block)), 1);
			return getItemDropNames();
		}

		Set<String> zeroDropItems = new HashSet<String>();
		Collection<ItemStack> coll = new ArrayList<ItemStack>();
		coll = block.getDrops(new ItemStack(Material.DIAMOND_PICKAXE));

		if (coll.isEmpty() || dropsAreInconsistent(block)) {
			String name = block.getType().toString();

			if (name.equalsIgnoreCase("DEAD_BUSH")) {
				zeroDropItems.add("STICK");

			} else if (name.equalsIgnoreCase("CHORUS_PLANT")) {
				zeroDropItems.add("CHORUS_FRUIT");

			} else if (name.equalsIgnoreCase("GRASS") || name.equalsIgnoreCase("TALL_GRASS") || name.contains("FERN")) {
				zeroDropItems.add("WHEAT_SEEDS");

			} else if (name.equalsIgnoreCase("RED_MUSHROOM_BLOCK")) {
				zeroDropItems.add("RED_MUSHROOM");

			} else if (name.equalsIgnoreCase("BROWN_MUSHROOM_BLOCK")) {
				zeroDropItems.add("BROWN_MUSHROOM");

			} else if (name.contains("MELON_STEM")) {
				zeroDropItems.add("MELON_SEEDS");

			} else if (name.contains("PUMPKIN_STEM")) {
				zeroDropItems.add("PUMPKIN_SEEDS");

			} else if (name.equalsIgnoreCase("ACACIA_LEAVES")) {
				zeroDropItems.add("ACACIA_SAPLING");

			} else if (name.equalsIgnoreCase("AZALEA_LEAVES")) {
				zeroDropItems.add("AZALEA");

			} else if (name.equalsIgnoreCase("BIRCH_LEAVES")) {
				zeroDropItems.add("BIRCH_SAPLING");

			} else if (name.equalsIgnoreCase("DARK_OAK_LEAVES")) {
				zeroDropItems.add("DARK_OAK_SAPLING");
				zeroDropItems.add("APPLE");

			} else if (name.equalsIgnoreCase("FLOWERING_AZALEA_LEAVES")) {
				zeroDropItems.add("FLOWERING_AZALEA");

			} else if (name.equalsIgnoreCase("JUNGLE_LEAVES")) {
				zeroDropItems.add("JUNGLE_SAPLING");

			} else if (name.equalsIgnoreCase("OAK_LEAVES")) {
				zeroDropItems.add("OAK_SAPLING");
				zeroDropItems.add("APPLE");

			} else if (name.equalsIgnoreCase("SPRUCE_LEAVES")) {
				zeroDropItems.add("SPRUCE_SAPLING");

			} else if (name.equalsIgnoreCase("CHORUS_FLOWER")) {
				zeroDropItems.add(name);

			} else if (name.contains("_VINES")) {
				zeroDropItems.add(name);

			} else {
				// items like GLASS drop nothing
				zeroDropItems.add("");
			}

			if (name.contains("LEAVES")) {
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
	 *
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
	 *
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

		} else if (item.equalsIgnoreCase("APPLE") || item.contains("SAPLING") || item.contains("AZALEA")) {
			if (block.getType().toString().contains("LEAVES")) {
				separator = " # 0  ->";
			}

		} else if (item.equalsIgnoreCase("WHEAT_SEEDS") && block.getType() != Material.WHEAT) {
			separator = " # 0  ->";

		} else if (item.equalsIgnoreCase("RED_MUSHROOM") || item.equalsIgnoreCase("BROWN_MUSHROOM")) {
			if (block.getType().toString().contains("MUSHROOM_BLOCK")) {
				separator = " # 0  ->";
			}

		} else if (item.contains("_VINES")) {
			separator = " # 0  ->";
		}

		// Scoreboard max length is 40, so subtract length of result and separator
		int maxlen = 40 - result.length() - separator.length();

		String translated = null;
		if (isSlimefunBlock(block) || isNovaBlock(block) || isItemsAdderBlock(block) || isOraxenBlock(block) || isCraftoryBlock(block)) {
			translated = item;
		} else {
			translated = translateItemName(item, player);
		}

		return result + translated.substring(0, Math.min(translated.length(), maxlen)) + separator;
	}

	private boolean isSlimefunBlock(Block block) {
		if (!slimefun) {
			return false;
		}
		return SlimefunHandler.isSlimefun(block.getLocation());
	}

	private boolean isNovaBlock(Block block) {
		if (!nova) {
			return false;
		}
		return NovaHandler.isNova(block.getLocation());
	}

	private boolean isItemsAdderBlock(Block block) {
		if (!itemsadder) {
			return false;
		}
		return CustomBlock.byAlreadyPlaced(block) != null;
	}

	private boolean isOraxenBlock(Block block) {
		if (!oraxen) {
			return false;
		}
		return OraxenHandler.isOraxen(block);
	}

	private boolean isCraftoryBlock(Block block) {
		if (!craftory) {
			return false;
		}
		return CraftoryHandler.isCraftory(block.getLocation());
	}

	public String getCustomResourceName(Block block) {
		if (isSlimefunBlock(block)) {
			return "Slimefun";
		} else if (isNovaBlock(block)) {
			return "Nova";
		} else if (isItemsAdderBlock(block)) {
			return "ItemsAdder";
		} else if (isOraxenBlock(block)) {
			return "Oraxen";
		} else if (isCraftoryBlock(block)) {
			return "Craftory";
		}
		return "";
	}

	public String getCustomPrefix(Block block) {
		String name = getCustomResourceName(block);
		return !name.isEmpty() ? plugin.getSettings().getCustomPrefix().replace("{PREFIX}", name) : "";
	}

	/**
	 * Returns whether or not this block drops a variable amount, including zero, of the item?
	 * Include bugged items like chorus_flower which always drops itself, but getDrops() returns nothing.
	 *
	 * @param block
	 * @return boolean
	 */
	private boolean dropsAreInconsistent(Block block) {
		return Enums.getIfPresent(InconsistentDropItems.class, block.getType().toString()).orNull() != null;
	}

	/**
	 * Get the amount of the item that can be dropped. If the item can sometimes drop zero items,
	 * then get the maximum number for that item.
	 *
	 * @param block
	 * @param name
	 * @return amount
	 */
	public int getAmount(Block block, String name) {
		if (!dropsAreInconsistent(block)) {
			return itemDrops.get(name) != null ? itemDrops.get(name) : 0;
		}

		int amount = switch (block.getType().toString()) {
			case "MELON_STEM", "PUMPKIN_STEM", "ATTACHED_MELON_STEM", "ATTACHED_PUMPKIN_STEM" -> 3;
			case "RED_MUSHROOM_BLOCK", "BROWN_MUSHROOM_BLOCK" -> 2;
			default -> {
				yield name.equalsIgnoreCase("STICK") ? 2 : 1;
			}
		};
		return amount;
	}

	private Set<String> getItemDropNames() {
		return itemDrops.keySet();
	}
}

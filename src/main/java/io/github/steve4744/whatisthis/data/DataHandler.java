/*
 * MIT License

Copyright (c) 2022 steve4744

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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.google.common.base.Enums;

import io.github.steve4744.whatisthis.WhatIsThis;
import io.github.steve4744.whatisthis.lang.EnumLang;
import io.github.steve4744.whatisthis.utils.OraxenHandler;
import io.github.steve4744.whatisthis.utils.SlimefunHandler;
import io.github.steve4744.whatisthis.utils.Utils;
import io.github.steve4744.whatisthis.utils.CraftoryHandler;
import io.github.steve4744.whatisthis.utils.ItemsAdderHandler;
import io.github.steve4744.whatisthis.utils.MythicMobsHandler;
import io.github.steve4744.whatisthis.utils.NovaHandler;

public class DataHandler {

	private WhatIsThis plugin;
	private boolean nova;
	private boolean slimefun;
	private boolean itemsadder;
	private boolean oraxen;
	private boolean craftory;
	private boolean mythicmobs;

	private static final double DEFAULT_HEALTH = 1.0;
	private static final String SLIMEFUN = "Slimefun";
	private static final String NOVA = "Nova";
	private static final String ITEMSADDER = "ItemsAdder";
	private static final String ORAXEN = "Oraxen";
	private static final String CRAFTORY = "Craftory";
	private static final String MYTHICMOBS = "MythicMobs";

	private Map<String, Integer> itemDrops = new HashMap<>();  // material -> amount

	public DataHandler(WhatIsThis plugin) {
		this.plugin = plugin;
		PluginManager pm = plugin.getServer().getPluginManager();
		this.slimefun = pm.isPluginEnabled(SLIMEFUN);
		this.nova = pm.isPluginEnabled(NOVA);
		this.itemsadder = pm.isPluginEnabled(ITEMSADDER);
		this.oraxen = pm.isPluginEnabled(ORAXEN);
		this.craftory = pm.isPluginEnabled(CRAFTORY);
		this.mythicmobs = pm.isPluginEnabled(MYTHICMOBS);
	}

	/**
	 * Get the localised material name. Cater for vanilla issues with WALL_ items.
	 *
	 * @param target material name
	 * @param player
	 * @return localised material name
	 */
	public String getDisplayName(Block block, Player player) {
		if (isBlacklisted(block)) {
			return "";
		}
		String targetName = null;
		if (isSlimefunBlock(block)) {
			targetName = includePlugin(SLIMEFUN) ? ChatColor.stripColor(SlimefunHandler.getDisplayName(block)) : "";

		} else if (isNovaBlock(block)) {
			targetName = includePlugin(NOVA) ? ChatColor.stripColor(NovaHandler.getDisplayName(block.getLocation(), Utils.getLocale(player))) : "";

		} else if (isItemsAdderBlock(block)) {
			targetName = includePlugin(ITEMSADDER) ? ChatColor.stripColor(ItemsAdderHandler.getDisplayName(block)) : "";

		} else if (isOraxenBlock(block)) {
			targetName = includePlugin(ORAXEN) ? ChatColor.stripColor(OraxenHandler.getDisplayName(block)) : "";

		} else if (isCraftoryBlock(block)) {
			targetName = includePlugin(CRAFTORY) ? ChatColor.stripColor(CraftoryHandler.getDisplayName(block)) : "";

		} else {
			if (block.getType() == Material.PLAYER_HEAD || block.getType() == Material.PLAYER_WALL_HEAD) {
				Skull skull = (Skull) block.getState();
				if (skull.getOwningPlayer() != null) {
					String name = skull.getOwningPlayer().getName();
					return name != null ? name + "'s Head" : "";
				}
			}
			targetName = block.getType().toString();
			//coloured wall_banners are not currently in the Mojang language files
			if (targetName.contains("WALL_BANNER")) {
				targetName = targetName.replace("WALL_", "");
			}
		}

		String translatedName = isCustomBlock(block) ? targetName : translateItemName(targetName, player);

		if (plugin.getSettings().isCustomDataEnabled()) {
			String prefix = getCustomResourceName(block) != "" ? getCustomResourceName(block).toLowerCase() + "_" : "minecraft_";
			return plugin.getCustomData().hasCustomName(prefix + translatedName) ?
					plugin.getCustomData().getCustomName(prefix + translatedName) : translatedName;
		}

		return translatedName;
	}

	private String getEntityDisplayName(Entity entity, Player player) {
		if (isBlacklistedEntity(entity)) {
			return "";
		}
		if (entity instanceof Player) {
			Player targetPlayer = (Player) entity;
			return targetPlayer.getName();
		}
		String targetName = translateEntityName(entity.getType().toString(), player);

		if (isItemsAdderEntity(entity)) {
			targetName = includePlugin(ITEMSADDER) ? ChatColor.stripColor(ItemsAdderHandler.getEntityDisplayName(entity)) : "";

		} else if (isMythicMobsEntity(entity)) {
			targetName =  includePlugin(MYTHICMOBS) ? ChatColor.stripColor(MythicMobsHandler.getEntityDisplayName(entity)) : "";
		}

		if (isHybridEntity(entity.getType().toString())) {
			return getVariant(entity) + " " + targetName;
		}
		return targetName;
	}

	private double getEntityHealthNormalised(Entity entity) {
		if (!(entity instanceof Damageable) || isBlacklistedEntity(entity)) {
			return DEFAULT_HEALTH;
		}
		if (isMythicMobsEntity(entity)) {
			return MythicMobsHandler.getEntityHealthNormalised(entity);
		}
		LivingEntity le = (LivingEntity) entity;
		double maxhealth = le.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

		return ((Damageable) entity).getHealth() / maxhealth;
	}

	public void processBlock(Block block, Player player) {
		String prefix = plugin.getSettings().isCustomPrefixEnabled() ? getCustomPrefix(block, null) : "";
		plugin.getDisplayHandler().getVisualMethod(prefix, getDisplayName(block, player), player, block, DEFAULT_HEALTH);
	}

	public void processEntity(Entity entity, Player player) {
		if (entity.getType().toString().equalsIgnoreCase("dropped_item")) {
			return;
		}
		String prefix = plugin.getSettings().isCustomPrefixEnabled() ? getCustomPrefix(null, entity) : "";
		plugin.getDisplayHandler().getVisualMethod(prefix, getEntityDisplayName(entity, player), player, null, getEntityHealthNormalised(entity));
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
			itemDrops.put(ChatColor.stripColor(SlimefunHandler.getDisplayName(block)), 1);
			return getItemDropNames();
		}
		if (isNovaBlock(block)) {
			itemDrops.put(ChatColor.stripColor(NovaHandler.getDisplayName(block.getLocation(), Utils.getLocale(player))), 1);
			return getItemDropNames();
		}
		if (isItemsAdderBlock(block)) {
			itemDrops.put(ChatColor.stripColor(ItemsAdderHandler.getDisplayName(block)), 1);
			return getItemDropNames();
		}
		if (isOraxenBlock(block)) {
			itemDrops.put(ChatColor.stripColor(OraxenHandler.getDisplayName(block)), 1);
			return getItemDropNames();
		}
		if (isCraftoryBlock(block)) {
			itemDrops.put(ChatColor.stripColor(CraftoryHandler.getDisplayName(block)), 1);
			return getItemDropNames();
		}

		Set<String> zeroDropItems = new HashSet<String>();

		if (hasDropRange(block)) {
			Map<String, String> itemDrops = ItemDropRanges.valueOf(block.getType().toString()).getMap();
			zeroDropItems.addAll(itemDrops.keySet());
			return zeroDropItems;
		}

		Collection<ItemStack> coll = new ArrayList<ItemStack>();
		coll = block.getDrops(new ItemStack(getPreferredTool(block)), player);
		if (coll.isEmpty()) {
			zeroDropItems.add("");
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
	public String translateItemName(String item, Player player) {
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

		return translated == null ? "block not found:" + item : translated;
	}

	private String translateEntityName(String name, Player player) {
		String locale = Utils.getLocale(player);
		String translated = null;

		if (EnumLang.get(locale).getMap().containsKey("entity.minecraft." + name.toLowerCase())) {
			translated = EnumLang.get(locale).getMap().get("entity.minecraft." + name.toLowerCase());

		} else if (EnumLang.get("en_us").getMap().containsKey("entity.minecraft." + name.toLowerCase())) {
			translated = EnumLang.get("en_us").getMap().get("entity.minecraft." + name.toLowerCase());
		}

		return translated == null ? "entity not found:" + name : translated;
	}

	public boolean isCustomBlock(Block block) {
		return isSlimefunBlock(block) || isNovaBlock(block) || isItemsAdderBlock(block) || isOraxenBlock(block) || isCraftoryBlock(block);
	}

	private boolean isSlimefunBlock(Block block) {
		return slimefun ? SlimefunHandler.isSlimefun(block.getLocation()) : false;
	}

	private boolean isNovaBlock(Block block) {
		return nova ? NovaHandler.isNova(block.getLocation()) : false;
	}

	private boolean isItemsAdderBlock(Block block) {
		return itemsadder ? ItemsAdderHandler.isItemsAdder(block) : false;
	}

	private boolean isItemsAdderEntity(Entity entity) {
		return itemsadder ? ItemsAdderHandler.isItemsAdderEntity(entity) : false;
	}

	private boolean isOraxenBlock(Block block) {
		return oraxen ? OraxenHandler.isOraxen(block) : false;
	}

	private boolean isCraftoryBlock(Block block) {
		return craftory ? CraftoryHandler.isCraftory(block.getLocation()) : false;
	}

	private boolean isMythicMobsEntity(Entity entity) {
		return mythicmobs ? MythicMobsHandler.isMythicMobs(entity) : false;
	}

	/**
	 * Get the name of the plugin providing the custom block.
	 *
	 * @param block
	 * @return the name of the custom resource
	 */
	public String getCustomResourceName(Block block) {
		if (isSlimefunBlock(block)) {
			return SLIMEFUN;

		} else if (isNovaBlock(block)) {
			return NOVA;

		} else if (isItemsAdderBlock(block)) {
			return ITEMSADDER;

		} else if (isOraxenBlock(block)) {
			return ORAXEN;

		} else if (isCraftoryBlock(block)) {
			return CRAFTORY;
		}
		return "";
	}

	public String getCustomResourceName(Entity entity) {
		if (isItemsAdderEntity(entity)) {
			return ITEMSADDER;
		} else if (isMythicMobsEntity(entity)) {
			return MYTHICMOBS;
		}
		return "";
	}

	/**
	 * Get the prefix to prepend to the custom block name.
	 *
	 * @param block
	 * @return prefix
	 */
	public String getCustomPrefix(Block block, Entity entity) {
		String name = block != null ? getCustomResourceName(block) : getCustomResourceName(entity);
		return !name.isEmpty() ? plugin.getSettings().getCustomPrefix().replace("{PREFIX}", name) : "";
	}

	/**
	 * Returns whether or not this block drops a variable amount of an item.
	 *
	 * @param block
	 * @return boolean
	 */
	public boolean hasDropRange(Block block) {
		return Enums.getIfPresent(ItemDropRanges.class, block.getType().toString()).orNull() != null;
	}

	/**
	 * Get the amount of the item that can be dropped or zero.
	 *
	 * @param block
	 * @param name
	 * @return amount
	 */
	public int getFixedAmount(String name) {
		return itemDrops.get(name) != null ? itemDrops.get(name) : 0;
	}

	private Set<String> getItemDropNames() {
		return itemDrops.keySet();
	}

	private Material getPreferredTool(Block block) {
		return block.getType().toString().contains("SNOW") ? Material.DIAMOND_SHOVEL : Material.DIAMOND_PICKAXE;
	}

	/**
	 * Return true if the block is blacklisted or if the block is null.
	 *
	 * @param block
	 * @return
	 */
	public boolean isBlacklisted(Block block) {
		return block == null || plugin.getSettings().getBlacklist().contains(block.getType().toString());
	}

	/**
	 * Return true if the entity is blacklisted or if the entity is null.
	 *
	 * @param entity
	 * @return
	 */
	public boolean isBlacklistedEntity(Entity entity) {
		return entity == null || plugin.getSettings().getEntityBlacklist().contains(entity.getType().toString());
	}

	/**
	 * Return true if the plugin's blocks and/or entities are to be included.
	 *
	 * @param string plugin name
	 * @ return
	 */
	private boolean includePlugin(String pluginName) {
		return !plugin.getConfig().getStringList("IgnorePlugins").contains(pluginName);
	}

	/**
	 * Return the variant name or type of entity.
	 *
	 * @param entity
	 * @return string variant name
	 */
	private String getVariant(Entity entity) {
		String type = switch (entity.getType().toString().toUpperCase()) {
		case "AXOLOTL" -> {
			yield ((Axolotl)entity).getVariant().toString();
		}
		case "BOAT" -> {
			yield ((Boat)entity).getBoatType().toString();
		}
		case "CAT" -> {
			yield ((Cat)entity).getCatType().toString();
		}
		case "FOX" -> {
			yield ((Fox)entity).getFoxType().toString();
		}
		case "FROG" -> {
			yield ((Frog)entity).getVariant().toString();
		}
		case "HORSE" -> {
			String colour = ((Horse)entity).getColor().toString();
			String style = ((Horse)entity).getStyle().toString();
			if (style.equalsIgnoreCase("none")) {
				style = "";
			}
			yield colour + " " + style;
		}
		case "LLAMA" -> {
			yield ((Llama)entity).getColor().toString();
		}
		case "MUSHROOMCOW" -> {
			yield ((MushroomCow)entity).getVariant().toString();
		}
		case "PARROT" -> {
			yield ((Parrot)entity).getVariant().toString();
		}
		case "RABBIT" -> {
			yield ((Rabbit)entity).getRabbitType().toString();
		}
		case "TROPICALFISH" -> {
			String pattern = ((TropicalFish)entity).getPattern().toString();
			String colour = ((TropicalFish)entity).getPatternColor().toString();
			if (pattern.equalsIgnoreCase("none")) {
				pattern = "";
			}
			yield colour + " " + pattern;
		}
		case "VILLAGER" -> {
			String profession = ((Villager)entity).getProfession().toString();
			String vtype = ((Villager)entity).getVillagerType().toString();
			if (profession.equalsIgnoreCase("none")) {
				profession = "";
			}
			yield vtype + " " + profession;
		}
		default -> {
			yield entity.getType().toString();
		}
		};
		return Utils.capitalizeFully(type);
	}

	private boolean isHybridEntity(String name) {
		return Arrays.stream(EntityTypes.values()).anyMatch((t) -> t.name().equalsIgnoreCase(name));
	}
}

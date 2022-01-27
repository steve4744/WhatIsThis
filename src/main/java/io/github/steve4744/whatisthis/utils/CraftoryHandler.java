package io.github.steve4744.whatisthis.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

import tech.brettsaunders.craftory.Craftory;
import tech.brettsaunders.craftory.Utilities;
import tech.brettsaunders.craftory.api.blocks.CustomBlockManager;

public class CraftoryHandler {
	
	private static CustomBlockManager manager = Craftory.customBlockManager;

	public static boolean isCraftory(Location loc) {
		return manager.isCustomBlock(loc);
	}

	public static String getCraftoryDisplayName(Block block) {
		return Utilities.getTranslation(manager.getCustomBlockName(block.getLocation()));
	}
}

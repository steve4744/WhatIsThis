package io.github.steve4744.whatisthis.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import com.nexomc.nexo.api.NexoBlocks;
import com.nexomc.nexo.api.NexoFurniture;

public class NexoHandler {

	public static boolean isNexo(Block block) {
		return NexoBlocks.isNexoNoteBlock(block) || NexoFurniture.isFurniture(block.getLocation());
	}

	public static boolean isNexo(Entity entity) {
		return NexoFurniture.isFurniture(entity);
	}

	public static String getDisplayName(Block block) {
		if (NexoBlocks.isNexoNoteBlock(block)) {
			return Utils.capitalizeFully(NexoBlocks.noteBlockMechanic(block).getItemID());

		} else if (NexoBlocks.isNexoStringBlock(block)) {
			return Utils.capitalizeFully(NexoBlocks.stringMechanic(block).getItemID());

		} else if (NexoBlocks.isCustomBlock(block)) {
			return Utils.capitalizeFully(NexoBlocks.customBlockMechanic(block.getLocation()).getItemID());

		} else if (NexoFurniture.isFurniture(block.getLocation())) {
			return Utils.capitalizeFully(NexoFurniture.furnitureMechanic(block).getItemID());
		}

		return "";
	}

	public static String getEntityDisplayName(Entity entity) {
		if (isNexo(entity)) {
			return Utils.capitalizeFully(NexoFurniture.furnitureMechanic(entity).getItemID());
		}
		return "";
	}
}

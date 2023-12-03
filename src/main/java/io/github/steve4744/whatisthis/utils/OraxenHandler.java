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
package io.github.steve4744.whatisthis.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import io.th0rgal.oraxen.api.OraxenBlocks;
import io.th0rgal.oraxen.api.OraxenFurniture;

public class OraxenHandler {

	public static boolean isOraxen(Block block) {
		return OraxenBlocks.isOraxenBlock(block) || OraxenFurniture.isFurniture(block);
	}

	public static boolean isOraxen(Entity entity) {
		return OraxenFurniture.isFurniture(entity);
	}

	public static String getDisplayName(Block block) {
		if (OraxenBlocks.isOraxenNoteBlock(block)) {
			return Utils.capitalizeFully(OraxenBlocks.getNoteBlockMechanic(block).getItemID());

		} else if (OraxenBlocks.isOraxenStringBlock(block)) {
			return Utils.capitalizeFully(OraxenBlocks.getStringMechanic(block).getItemID());

		} else if (OraxenBlocks.isOraxenBlock(block)) {
			return Utils.capitalizeFully(OraxenBlocks.getBlockMechanic(block).getItemID());

		} else if (OraxenFurniture.isFurniture(block)) {
			return Utils.capitalizeFully(OraxenFurniture.getFurnitureMechanic(block).getItemID());
		}

		return "";
	}

	public static String getEntityDisplayName(Entity entity) {
		if (isOraxen(entity)) {
			return Utils.capitalizeFully(OraxenFurniture.getFurnitureMechanic(entity).getItemID());
		}
		return "";
	}
}

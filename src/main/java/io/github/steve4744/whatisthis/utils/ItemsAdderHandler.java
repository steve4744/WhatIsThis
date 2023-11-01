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

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomEntity;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomMob;

public class ItemsAdderHandler {

	public static boolean isItemsAdder(Block block) {
		return CustomBlock.byAlreadyPlaced(block) != null;
	}

	public static String getDisplayName(Block block) {
		return CustomBlock.byAlreadyPlaced(block).getDisplayName();
	}

	public static boolean isItemsAdderEntity(Entity entity) {
		return isApiEnabled() ? CustomEntity.isCustomEntity(entity) : false;
	}

	public static String getEntityDisplayName(Entity entity) {
		CustomMob customMob = CustomMob.byAlreadySpawned(entity);
		if (customMob != null) {
			return customMob.getName();
		}
		CustomFurniture customFurn = CustomFurniture.byAlreadySpawned(entity);
		if (customFurn != null) {
			return customFurn.getDisplayName();
		}
		return CustomEntity.byAlreadySpawned(entity).getNamespace();
	}

	/*
	 * ItemsAdder only loads the Entity API if its enabled in the config.
	 *
	 * @return is entity API available
	 */
	private static boolean isApiEnabled() {
		try {
			Class.forName("dev.lone.itemsadder.api.CustomEntity");
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
}

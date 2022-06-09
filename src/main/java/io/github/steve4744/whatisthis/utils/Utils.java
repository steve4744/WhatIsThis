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
package io.github.steve4744.whatisthis.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class Utils {

	public static boolean isAir(Material material) {
		return material == Material.AIR || material == Material.CAVE_AIR || material == Material.VOID_AIR;
	}

	public static boolean isWater(Material material) {
		return material == Material.WATER;
	}

	public static String getLocale(Player player) {
		return player.getLocale();
	}

	/**
	 * Get the block the player is looking at.
	 *
	 * @param player
	 * @return block targeted by player
	 */
	public static Block getTargetBlock(Player player) {
		BlockIterator iter = new BlockIterator(player, 10);
		Block lastBlock = iter.next();
		while (iter.hasNext()) {
			lastBlock = iter.next();
			if (Utils.isAir(lastBlock.getType()) || Utils.isWater(lastBlock.getType()))
				continue;
			break;
		}
		return lastBlock;
	}

	/**
	 * Get a string representation of the location of the targeted block.
	 *
	 * @param block
	 * @return
	 */
	public static String getLocationString(Block block) {
		return "XYZ: " + block.getX() + " / " + block.getY() + " / " + block.getZ();
	}

	/**
	 * Convert the text to title case replacing underscores with spaces.
	 *
	 * @param text
	 * @return name in title case
	 */
	public static String capitalizeFully(String text) {
		String delim = "\\s|_";

		return Stream.of(text.split(delim))
				.map(Utils::capitalize)
				.collect(Collectors.joining(" "));
	}

	private static String capitalize(String word) {
		return word.equals("") ? word : word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
	}
}

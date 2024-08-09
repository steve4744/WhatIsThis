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

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import net.md_5.bungee.api.ChatColor;

public class Utils {

	private final static Pattern HEXCOLOUR = Pattern.compile("<#([A-Fa-f0-9]){6}>");

	public static boolean isWater(Material material) {
		return material == Material.WATER;
	}

	public static String getLocale(Player player) {
		return player.getLocale();
	}

	/**
	 * Get the block the player is looking at. Ignore transparent blocks.
	 * The BlockIterator method can sometimes return an exception, so just ignore it.
	 *
	 * @param player
	 * @return block targeted by player
	 */
	public static Block getTargetBlock(Player player) {
		Set<Material> transparent = Set.of(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.WATER, Material.LIGHT);
		try {
			Block lastBlock = player.getTargetBlock(transparent, 10);
			return lastBlock;

		} catch(IllegalStateException ex) {
		}
		return null;
	}

	public static RayTraceResult getRayTraceResult(Player player) {
		return player.getWorld().rayTrace(player.getEyeLocation(),
				player.getEyeLocation().getDirection(),
				10.0,
				FluidCollisionMode.NEVER,
				false,
				0.1,
				entity -> !entity.getUniqueId().equals(player.getUniqueId()));
	}

	public static boolean isBlock(RayTraceResult result) {
		return result != null && result.getHitBlock() != null;
	}

	public static boolean isEntity(RayTraceResult result) {
		return result != null && result.getHitEntity() != null;
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

	public static String getLocationString(Entity entity) {
		return "XYZ: " + entity.getLocation().getBlockX() + " / " + entity.getLocation().getBlockY() + " / " + entity.getLocation().getBlockZ();
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

	public static String translateColourCodes(String message) {
		Matcher matcher = HEXCOLOUR.matcher(message);
		while (matcher.find()) {
			StringBuilder sb = new StringBuilder();
			final ChatColor hexColour = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
			sb.append(message.substring(0, matcher.start())).append(hexColour).append(message.substring(matcher.end()));
			message = sb.toString();
			matcher = HEXCOLOUR.matcher(message);
		}
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}

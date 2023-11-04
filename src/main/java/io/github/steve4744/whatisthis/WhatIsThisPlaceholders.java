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
package io.github.steve4744.whatisthis;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import io.github.steve4744.whatisthis.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class WhatIsThisPlaceholders extends PlaceholderExpansion {

	private final WhatIsThis plugin;

	public WhatIsThisPlaceholders(WhatIsThis plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean canRegister() {
		return true;
	}

	@Override
	public boolean persist(){
		return true;
	}

	@Override
	public String getAuthor() {
		return plugin.getDescription().getAuthors().toString();
	}

	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	@Override
	public String getIdentifier() {
		return "whatisthis";
	}

	@Override
	public String onRequest(OfflinePlayer p, String identifier) {

		if (p == null) {
			return "";
		}

		Player player = (Player) p;
		if (plugin.getConfig().getStringList("BlacklistedWorlds").contains(player.getWorld().getName())) {
			return "";
		}
		if (identifier.equals("version")) {
			return String.valueOf(plugin.getDescription().getVersion());

		} else if (identifier.equals("blockname")) {
			return plugin.getDataHandler().getDisplayName(Utils.getTargetBlock(player), player);

		} else if (identifier.equals("entityname")) {
			RayTraceResult result = Utils.getRayTraceResult(player);
			return Utils.isEntity(result) ? plugin.getDataHandler().getEntityDisplayName(result.getHitEntity(), player) : "";

		} else if (identifier.equals("resourcename")) {
			RayTraceResult result = Utils.getRayTraceResult(player);
			if (Utils.isBlock(result)) {
				return plugin.getDataHandler().getCustomResourceName(Utils.getTargetBlock(player));

			} else if (Utils.isEntity(result)) {
				return plugin.getDataHandler().getCustomResourceName(result.getHitEntity());
			}
			return "";

		} else if (identifier.startsWith("location")) {
			RayTraceResult result = Utils.getRayTraceResult(player);
			if (Utils.isBlock(result)) {
				Block block = result.getHitBlock();
				if (plugin.getDataHandler().isBlacklisted(block)) {
					return "";
				}
				if (identifier.endsWith("n")) {
					return Utils.getLocationString(block);

				} else if (identifier.endsWith("X")) {
					return String.valueOf(block.getLocation().getBlockX());

				} else if (identifier.endsWith("Y")) {
					return String.valueOf(block.getLocation().getBlockY());

				} else if (identifier.endsWith("Z")) {
					return String.valueOf(block.getLocation().getBlockZ());
				}

			} else if (Utils.isEntity(result)) {
				if (plugin.getDataHandler().isBlacklistedEntity(result.getHitEntity())) {
					return "";
				}
				if (identifier.endsWith("n")) {
					return Utils.getLocationString(result.getHitEntity());

				} else if (identifier.endsWith("X")) {
					return String.valueOf(result.getHitEntity().getLocation().getBlockX());

				} else if (identifier.endsWith("Y")) {
					return String.valueOf(result.getHitEntity().getLocation().getBlockY());

				} else if (identifier.endsWith("Z")) {
					return String.valueOf(result.getHitEntity().getLocation().getBlockZ());
				}
			}
			return "";
		}
		return null;
	}
}

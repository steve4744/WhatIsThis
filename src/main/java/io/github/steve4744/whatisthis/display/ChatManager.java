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
package io.github.steve4744.whatisthis.display;

import java.util.Map;
import java.util.StringJoiner;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import io.github.steve4744.whatisthis.WhatIsThis;
import io.github.steve4744.whatisthis.data.DataHandler;
import io.github.steve4744.whatisthis.data.ItemDropRanges;
import io.github.steve4744.whatisthis.utils.Utils;

public class ChatManager {

	private final WhatIsThis plugin;
	private DataHandler dataHandler;

	public ChatManager(WhatIsThis plugin) {
		this.plugin = plugin;
		this.dataHandler = plugin.getDataHandler();
	}

	public void showMessage(Player player, String message, String prefix, Block block) {
		if (!message.isEmpty()) {
			player.sendMessage(prefix + formattedName(message) + formattedDrops(block, player));
		}
	}

	private String formattedName(String name) {
		return ChatColor.valueOf(plugin.getSettings().getChatColor("name")) + Utils.capitalizeFully(name);
	}

	private String formattedDrops(Block block, Player player) {
		if (!plugin.getSettings().showDropsInChat() || block == null) {
			return "";
		}

		StringJoiner drops = new StringJoiner(", ");
		for (String drop : dataHandler.getItemDrops(block, player)) {
			String translated = dataHandler.isCustomBlock(block) ? drop : dataHandler.translateItemName(drop, player);
			if (dataHandler.isCustomBlock(block) || !dataHandler.hasDropRange(block)) {
				int amount = dataHandler.getFixedAmount(drop);
				if (amount > 0) {
					drops.add(getFinalisedString(translated) + " x " + amount);
				}
			} else {
				Map<String, String> itemDrops = ItemDropRanges.valueOf(block.getType().toString()).getMap();
				drops.add(getFinalisedString(translated) + " " + itemDrops.get(drop));
			}
		}
		return ChatColor.DARK_GRAY + " [" + ChatColor.valueOf(plugin.getSettings().getChatColor("drop")) +
						drops.toString() + ChatColor.DARK_GRAY + "]";
	}

	private String getFinalisedString(String drop) {
		return Utils.capitalizeFully(drop);
	}
}

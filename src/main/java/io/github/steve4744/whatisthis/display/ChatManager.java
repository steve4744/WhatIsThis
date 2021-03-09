package io.github.steve4744.whatisthis.display;

import java.util.StringJoiner;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import io.github.steve4744.whatisthis.WhatIsThis;
import io.github.steve4744.whatisthis.data.DataHandler;

public class ChatManager {

	private final WhatIsThis plugin;
	private DataHandler dataHandler;
	private final char[] delim = {' ', '_'};

	public ChatManager(WhatIsThis plugin) {
		this.plugin = plugin;
		this.dataHandler = plugin.getDataHandler();
	}

	public void showMessage(Player player, Block block) {
		String name = dataHandler.getDisplayName(block, player);
		player.sendMessage(formattedName(name) + ChatColor.DARK_GRAY + " [" + formattedDrops(block) + ChatColor.DARK_GRAY + "]");
	}

	private String formattedName(String name) {
		return ChatColor.valueOf(plugin.getSettings().getChatColor("name")) + WordUtils.capitalizeFully(name, delim);
	}

	private String formattedDrops(Block block) {
		StringJoiner drops = new StringJoiner(", ");

		for (String drop : dataHandler.getItemDrops(block)) {
			int amount = dataHandler.getAmount(block, drop);
			if (amount > 0) {
				drops.add(WordUtils.capitalizeFully(drop, delim) + " x " + amount);
			}
		}
		return ChatColor.valueOf(plugin.getSettings().getChatColor("drop")) + drops.toString();
	}
}

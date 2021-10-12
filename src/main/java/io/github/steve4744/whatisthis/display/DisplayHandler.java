/*
 * MIT License

Copyright (c) 2019 steve4744

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

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import io.github.steve4744.whatisthis.WhatIsThis;

public class DisplayHandler {

	private WhatIsThis plugin;

	public DisplayHandler(WhatIsThis plugin) {
		this.plugin = plugin;
	}

	/**
	 * This method handles the visual output of a given Block
	 * based on the configured visual method.
	 * 
	 * @param block		The targeted Block
	 * @param player	The Player to display the target to
	 */
	public void getVisualMethod(Block block, Player player) {
		String message = plugin.getDataHandler().getDisplayName(block, player);

		if (plugin.getSettings().isScoreboardEnabled()) {
			plugin.getScoreboardManager().showTarget(player, message, block);
		}

		if (plugin.getSettings().isActionBarEnabled()) {
			ActionBar actionBar = new ActionBar(ChatColor.valueOf(plugin.getSettings().getActionBarColor()) + message);
			actionBar.sendBar(player);
		}

		if (plugin.getSettings().isBossbarEnabled()) {
			BossBarManager bm = new BossBarManager(plugin);
			bm.setBar(player, message);
		}

		if (plugin.getSettings().isChatEnabled()) {
			ChatManager cm = new ChatManager(plugin);
			cm.showMessage(player, message, block);
		}
	}
}

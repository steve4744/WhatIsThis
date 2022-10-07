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

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import io.github.steve4744.whatisthis.WhatIsThis;

public class DisplayHandler {

	private WhatIsThis plugin;

	public DisplayHandler(WhatIsThis plugin) {
		this.plugin = plugin;
	}

	/**
	 * This method handles the visual output of a given Block based on
	 * the configured visual method(s).
	 *
	 * @param result	The targeted block or entity @notnull
	 * @param player	The player to display the target to
	 */
	public void getVisualMethod(String prefix, String message, Player player, Block block, double health) {
		if (message.isEmpty()) {
			return;
		}

		if (plugin.getSettings().isScoreboardEnabled()) {
			if (!plugin.getSettings().isScoreboardOnSneak() || (plugin.getSettings().isScoreboardOnSneak() && player.isSneaking())) {
				plugin.getScoreboardManager().showTarget(player, message, prefix, block);
			}
		}

		if (plugin.getSettings().isActionBarEnabled()) {
			if (!plugin.getSettings().isActionBarOnSneak() || (plugin.getSettings().isActionBarOnSneak() && player.isSneaking())) {
				ActionBar actionBar = new ActionBar(prefix + plugin.getSettings().getActionBarColour() + message);
				actionBar.sendBar(player);
			}
		}

		if (plugin.getSettings().isBossbarEnabled()) {
			if (!plugin.getSettings().isBossBarOnSneak() || (plugin.getSettings().isBossBarOnSneak() && player.isSneaking())) {
				BossBarManager bm = new BossBarManager(plugin);
				bm.setBar(player, message, prefix, health);
			}
		}

		if (plugin.getSettings().isChatEnabled()) {
			if (!plugin.getSettings().isChatOnSneak() || (plugin.getSettings().isChatOnSneak() && player.isSneaking())) {
				ChatManager cm = new ChatManager(plugin);
				cm.showMessage(player, message, prefix, block);
			}
		}
	}
}

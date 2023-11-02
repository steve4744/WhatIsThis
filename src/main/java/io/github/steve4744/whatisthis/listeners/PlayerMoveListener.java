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
package io.github.steve4744.whatisthis.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.RayTraceResult;

import io.github.steve4744.whatisthis.WhatIsThis;
import io.github.steve4744.whatisthis.utils.Utils;

public class PlayerMoveListener implements Listener {

	private final WhatIsThis plugin;

	public PlayerMoveListener(WhatIsThis plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!plugin.getSettings().isAutoDisplayEnabled()) {
			return;
		}
		Player player = event.getPlayer();
		if (!player.hasPermission("whatisthis.use")) {
			return;
		}
		RayTraceResult result = Utils.getRayTraceResult(player);

		if (Utils.isBlock(result)) {
			plugin.getDataHandler().processBlock(result.getHitBlock(), player);
		} else if (Utils.isEntity(result)) {
			plugin.getDataHandler().processEntity(result.getHitEntity(), player);
		}
	}
}

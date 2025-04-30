/*
 * MIT License

Copyright (c) 2025 steve4744

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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import com.cjcrafter.foliascheduler.TaskImplementation;

import io.github.steve4744.whatisthis.WhatIsThis;

public class BossBarManager {

	private WhatIsThis plugin;
	private static Map<String, BossBar> barmap = new HashMap<>();
	private static Map<String, TaskImplementation<Void>> taskmap = new HashMap<>();
	private TaskImplementation<Void> bossBarTask;

	public BossBarManager(WhatIsThis plugin) {
		this.plugin = plugin;
	}

	private void createBar(Player player) {
		String colour = plugin.getSettings().getBossBarColor();
		String style = plugin.getSettings().getBossBarStyle();
		BossBar bar = Bukkit.createBossBar(null, BarColor.valueOf(colour), BarStyle.valueOf(style));
		bar.addPlayer(player);
		barmap.put(player.getName(), bar);
	}

	private void removePlayerFromBar(Player player) {
		BossBar bar = barmap.get(player.getName());
		if (bar != null) {
			bar.removePlayer(player);
			barmap.remove(player.getName());
			bar = null;
		}
	}

	public void setBar(Player player, String message, String prefix, double progress) {
		//kill any previous scheduled tasks
		cancelTask(player.getName());

		if (message.isEmpty()) {
			removePlayerFromBar(player);
			return;
		}

		if (!barmap.containsKey(player.getName())) {
			createBar(player);
		}
		String colour = plugin.getSettings().getBossBarTextColor();
		barmap.get(player.getName()).setTitle(prefix + ChatColor.valueOf(colour) + message);
		barmap.get(player.getName()).setProgress(progress);

		bossBarTask = plugin.getFoliaScheduler().global().runDelayed(() -> {
			removePlayerFromBar(player);
		}, plugin.getSettings().getBossBarTimeout());

		taskmap.put(player.getName(), bossBarTask);
	}

	private void cancelTask(String playername) {
		if (taskmap.containsKey(playername)) {
			taskmap.get(playername).cancel();
			taskmap.remove(playername);
		}
	}

}

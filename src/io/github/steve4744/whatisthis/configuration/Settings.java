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
package io.github.steve4744.whatisthis.configuration;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import com.google.common.base.Enums;

import io.github.steve4744.whatisthis.WhatIsThis;

public class Settings {

	private final WhatIsThis plugin;
	private boolean isClickEnabled;

	public Settings(WhatIsThis plugin) {
		this.plugin = plugin;
		this.isClickEnabled = plugin.getConfig().getBoolean("use_right_click.enabled", true);
	}

	public Material getClickItem() {
		Material clickItem = Material.getMaterial(plugin.getConfig().getString("use_right_click.item").toUpperCase());
		return clickItem != null ? clickItem : Material.AIR;
	}

	public List<String> getLangs() {
		String fallback = "en_us";
		List<String> langs = plugin.getConfig().getStringList("LoadLanguage");
		if (langs.contains("all")) {
			return langs;
		}
		if (!langs.contains(fallback)) {
			langs.add(fallback);
		}
		return langs;
	}

	public boolean isRightClickEnabled() {
		return isClickEnabled;
	}

	public void toggleRightClick() {
		isClickEnabled = !isClickEnabled;
	}

	public boolean isScoreboardEnabled() {
		return plugin.getConfig().getBoolean("Display.scoreboard.enabled", true);
	}

	public boolean isActionBarEnabled() {
		return plugin.getConfig().getBoolean("Display.actionbar.enabled", true);
	}

	public boolean isBossbarEnabled() {
		return plugin.getConfig().getBoolean("Display.bossbar.enabled", true);
	}
	
	public String getActionBarColor() {
		String colour = plugin.getConfig().getString("Display.actionbar.textcolor").toUpperCase();
		if (colour == null || Enums.getIfPresent(ChatColor.class, colour).orNull() == null) {
			colour = "WHITE";
		}
		return colour;
	}

	public String getBossBarColor() {
		String colour = plugin.getConfig().getString("Display.bossbar.barcolor").toUpperCase();
		if (colour == null || Enums.getIfPresent(BarColor.class, colour).orNull() == null) {
			colour = "GREEN";
		}
		return colour;
	}

	public String getBossBarTextColor() {
		String colour = plugin.getConfig().getString("Display.bossbar.textcolor").toUpperCase();
		if (colour == null || Enums.getIfPresent(BarColor.class, colour).orNull() == null) {
			colour = "WHITE";
		}
		return colour;
	}

}

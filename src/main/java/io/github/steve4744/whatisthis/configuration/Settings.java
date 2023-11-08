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
package io.github.steve4744.whatisthis.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;
import com.google.common.base.Enums;

import io.github.steve4744.whatisthis.WhatIsThis;
import io.github.steve4744.whatisthis.utils.Utils;

public class Settings {

	private final WhatIsThis plugin;
	private boolean isClickEnabled;
	private String actionBarColour;
	private String customPrefix;
	private FileConfiguration config;
	private Map<String, List<String>> customBlacklist = new HashMap<>();

	public Settings(WhatIsThis plugin) {
		this.plugin = plugin;
		config = plugin.getConfig();
		this.isClickEnabled = config.getBoolean("use_right_click.enabled", true);
		this.actionBarColour = loadActionBarColour();
		this.customPrefix = loadCustomPrefix();
		loadCustomBlacklist();
	}

	public Material getClickItem() {
		Material clickItem = Material.getMaterial(config.getString("use_right_click.item").toUpperCase());
		return clickItem != null ? clickItem : Material.AIR;
	}

	public List<String> getLangs() {
		String fallback = "en_us";
		List<String> langs = config.getStringList("LoadLanguage");
		if (langs.contains("all")) {
			return langs;
		}
		if (!langs.contains(fallback)) {
			langs.add(fallback);
		}
		return langs;
	}

	public List<String> getBlacklist() {
		return config.getStringList("IgnoreBlocks");
	}

	public List<String> getEntityBlacklist() {
		return config.getStringList("IgnoreEntities");
	}

	public boolean isIgnoreAllBlocks() {
		return getBlacklist().stream().anyMatch("all"::equalsIgnoreCase);
	}

	public boolean isIgnoreAllEntities() {
		return getEntityBlacklist().stream().anyMatch("all"::equalsIgnoreCase);
	}

	public boolean isRightClickEnabled() {
		return isClickEnabled;
	}

	public void toggleRightClick() {
		isClickEnabled = !isClickEnabled;
		saveToggle();
	}

	private void saveToggle() {
		config.set("use_right_click.enabled", isRightClickEnabled());
		plugin.saveConfig();
	}

	public boolean isScoreboardEnabled() {
		return config.getBoolean("Display.scoreboard.enabled", true);
	}

	public boolean isScoreboardOnSneak() {
		return config.getBoolean("Display.scoreboard.on_sneak");
	}

	public boolean isActionBarEnabled() {
		return config.getBoolean("Display.actionbar.enabled", true);
	}

	public boolean isActionBarOnSneak() {
		return config.getBoolean("Display.actionbar.on_sneak");
	}

	public boolean isBossbarEnabled() {
		return config.getBoolean("Display.bossbar.enabled", true);
	}

	public boolean isBossBarOnSneak() {
		return config.getBoolean("Display.bossbar.on_sneak");
	}

	public boolean isChatEnabled() {
		return config.getBoolean("Display.chat.enabled", true);
	}

	public boolean isChatOnSneak() {
		return config.getBoolean("Display.chat.on_sneak");
	}

	public boolean isAutoDisplayEnabled() {
		return config.getBoolean("Display.auto_display", false);
	}

	public boolean showDropsInChat() {
		return config.getBoolean("Display.chat.show_drops", true);
	}

	public boolean isCustomPrefixEnabled() {
		return config.getBoolean("Display.prefix_custom_blocks.enabled");
	}

	public boolean isCustomDataEnabled() {
		return config.getBoolean("CustomData.enabled");
	}

	public boolean isItemFrameContentEnabled() {
		return config.getBoolean("ItemFrame.display_content");
	}

	private String loadActionBarColour() {
		String colour = config.getString("Display.actionbar.textcolor", "WHITE").toUpperCase();
		if (Enums.getIfPresent(ChatColor.class, colour).orNull() != null) {
			return ChatColor.valueOf(colour).toString();
		}
		return Utils.translateColourCodes(colour);
	}

	public String getActionBarColour() {
		return actionBarColour;
	}

	public String getBossBarColor() {
		String colour = config.getString("Display.bossbar.barcolor").toUpperCase();
		return validateBarColor(colour);
	}

	public String getBossBarTextColor() {
		String colour = config.getString("Display.bossbar.textcolor").toUpperCase();
		return validateChatColor(colour);
	}

	public int getBossBarTimeout() {
		return config.getInt("Display.bossbar.timeout", 60);
	}

	public String getBossBarStyle() {
		int segments = config.getInt("Display.bossbar.style", 1);
		return validateBarStyle(segments);
	}

	public int getScoreboardTimeout() {
		return config.getInt("Display.scoreboard.timeout", 60);
	}

	public String getChatColor(String element) {
		String colour = element.equalsIgnoreCase("name") ? config.getString("Display.chat.name_color") : config.getString("Display.chat.drop_color");
		return validateChatColor(colour.toUpperCase());
	}

	private String loadCustomPrefix() {
		return Utils.translateColourCodes(plugin.getConfig().getString("Display.prefix_custom_blocks.prefix", ""));
	}

	public String getCustomPrefix() {
		return customPrefix;
	}

	private String validateBarColor(String colour) {
		if (colour == null || Enums.getIfPresent(BarColor.class, colour).orNull() == null) {
			colour = "GREEN";
		}
		return colour;
	}

	private String validateChatColor(String colour) {
		if (colour == null || Enums.getIfPresent(ChatColor.class, colour).orNull() == null) {
			colour = "WHITE";
		}
		return colour;
	}

	private String validateBarStyle(int segments) {
		String style = "SEGMENTED_" + segments;
		if (segments == 1 || Enums.getIfPresent(BarStyle.class, style).orNull() == null) {
			style = "SOLID";
		}
		return style;
	}

	private void loadCustomBlacklist() {
		if (!config.isConfigurationSection("Ignore")) {
			return;
		}
		for (String cname : config.getConfigurationSection("Ignore").getKeys(false)) {
			customBlacklist.put(cname, config.getStringList("Ignore." + cname));
		}
	}

	public List<String> getCustomBlacklist(String customName) {
		return customBlacklist.get(customName) != null ? customBlacklist.get(customName) : List.of();
	}
}

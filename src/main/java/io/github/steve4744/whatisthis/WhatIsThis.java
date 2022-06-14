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
package io.github.steve4744.whatisthis;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.steve4744.whatisthis.commands.AutoTabCompleter;
import io.github.steve4744.whatisthis.commands.WhatIsThisCommand;
import io.github.steve4744.whatisthis.configuration.Settings;
import io.github.steve4744.whatisthis.data.CustomData;
import io.github.steve4744.whatisthis.data.DataHandler;
import io.github.steve4744.whatisthis.display.DisplayHandler;
import io.github.steve4744.whatisthis.display.ScoreboardManager;
import io.github.steve4744.whatisthis.lang.EnumLang;
import io.github.steve4744.whatisthis.listeners.PlayerMoveListener;
import io.github.steve4744.whatisthis.listeners.WhatIsThisListener;

public class WhatIsThis extends JavaPlugin {

	private static WhatIsThis instance;
	private String version;
	private ScoreboardManager scoreboardManager;
	private DataHandler dataHandler;
	private Settings settings;
	private DisplayHandler displayHandler;
	private CustomData customData;
	private boolean placeholderapi;
	private static final int BSTATS_PLUGIN_ID = 4079;

	@Override
	public void onEnable() {

		instance = this;
		this.saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();

		version = this.getDescription().getVersion();
		setupPlugin();

		EnumLang.init();
		new Metrics(this, BSTATS_PLUGIN_ID);
		checkForUpdate();
	}

	@Override
	public void onDisable() {
		EnumLang.clean();
		scoreboardManager = null;
		displayHandler = null;
		dataHandler = null;
		settings = null;
		getLogger().info("WhatIsThis has been disabled");
	}

	public static WhatIsThis getPlugin() {
		return instance;
	}

	public ScoreboardManager getScoreboardManager() {
		if (scoreboardManager == null) {
			scoreboardManager = new ScoreboardManager(this);
		}
		return scoreboardManager;
	}

	public DataHandler getDataHandler() {
		return dataHandler;
	}

	public DisplayHandler getDisplayHandler() {
		return displayHandler;
	}

	public CustomData getCustomData() {
		return customData;
	}

	private void setupPlugin() {
		getCommand("whatisthis").setExecutor(new WhatIsThisCommand(version, this));
		getCommand("whatisthis").setTabCompleter(new AutoTabCompleter());
		settings = new Settings(this);
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new WhatIsThisListener(this), this);

		customData = new CustomData(this);
		dataHandler = new DataHandler(this);
		displayHandler = new DisplayHandler(this);

		if (getSettings().isAutoDisplayEnabled()) {
			pm.registerEvents(new PlayerMoveListener(this), this);
		}

		Plugin PlaceholderAPI = pm.getPlugin("PlaceholderAPI");
		if (PlaceholderAPI != null && PlaceholderAPI.isEnabled() && getConfig().getBoolean("PlaceholderAPI.enabled")) {
			placeholderapi = true;
			getLogger().info("Successfully linked with PlaceholderAPI, version " + PlaceholderAPI.getDescription().getVersion());
			new WhatIsThisPlaceholders(this).register();
		}
	}

	public void reloadPlugin() {
		reloadConfig();
		refreshSettings();
		reloadCustomData();
	}

	private void refreshSettings() {
		this.settings = new Settings(this);
	}

	private void reloadCustomData() {
		customData = new CustomData(this);
	}

	public Settings getSettings() {
		return settings;
	}

	public boolean isPlaceholderAPI() {
		return placeholderapi;
	}

	private void checkForUpdate() {
		if (!getConfig().getBoolean("check_for_update", true)) {
			return;
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				String latestVersion = VersionChecker.getVersion();
				if (latestVersion == "error") {
					getLogger().info("Error attempting to check for new version. Please report it here: https://www.spigotmc.org/threads/whatisthis.360832/");
				} else {
					if (!version.equals(latestVersion)) {
						getLogger().info("Latest version " + latestVersion + " available on Spigot: https://www.spigotmc.org/resources/whatisthis.65050//");
					}
				}
			}
		}.runTaskLaterAsynchronously(this, 40L);
	}

}

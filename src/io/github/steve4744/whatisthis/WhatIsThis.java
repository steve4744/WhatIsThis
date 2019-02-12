package io.github.steve4744.whatisthis;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.steve4744.whatisthis.metrics.Metrics;

public class WhatIsThis extends JavaPlugin {

	private static WhatIsThis instance;
	private String version;
	private ScoreboardManager scoreboardManager;

	@Override
	public void onEnable() {

		instance = this;
		this.saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();

		version = this.getDescription().getVersion();
		setupPlugin();

		new Metrics(this);
		checkForUpdate();
	}

	@Override
	public void onDisable() {
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

	private void setupPlugin() {
		this.getCommand("whatisthis").setExecutor(new WhatIsThisCommand(version, this));
		if (getConfig().getBoolean("use_right_click.enabled")) {
			PluginManager pm = Bukkit.getPluginManager();
			pm.registerEvents(new WhatIsThisListener(this), this);
		}
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
					getLogger().info("Error attempting to check for new version. Please report it here: https://www.spigotmc.org/threads/whatisthis.xxxxx/");
				} else {
					if (!version.equals(latestVersion)) {
						getLogger().info("New version " + latestVersion + " available on Spigot: https://www.spigotmc.org/resources/whatisthis.zzzzz/");
					}
				}
			}
		}.runTaskLaterAsynchronously(this, 40L);
	}

}

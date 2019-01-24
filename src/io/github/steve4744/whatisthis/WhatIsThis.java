package io.github.steve4744.whatisthis;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


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
	}

	@Override
	public void onDisable() {
		getLogger().info("WhatIsThis has been disabled");
	}

	public static WhatIsThis getPlugin() {
		return instance;
    }

	public static ScoreboardManager getScoreboardManager() {
        if (getPlugin().scoreboardManager == null) {
            getPlugin().scoreboardManager = new ScoreboardManager(instance);
        }
        return getPlugin().scoreboardManager;
    }

	private void setupPlugin() {
		this.getCommand("whatisthis").setExecutor(new WhatIsThisCommand());
		if (getConfig().getBoolean("use_right_click.enabled")) {
			PluginManager pm = Bukkit.getPluginManager();
			pm.registerEvents(new WhatIsThisListener(), this);
		}
	}

}

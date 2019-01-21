package io.github.steve4744.whatisthis;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class WhatIsThis extends JavaPlugin {
	
	private String version;

	@Override
	public void onEnable() {
		
		this.saveDefaultConfig();
		version = this.getDescription().getVersion();
		
		PluginManager pm = Bukkit.getPluginManager();
		this.getCommand("whatisthis").setExecutor(new WhatIsThisCommand());
		
	}

	@Override
	public void onDisable() {
		getLogger().info("WhatIsThis disabled");
	}

}

package io.github.steve4744.whatisthis.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.steve4744.whatisthis.WhatIsThis;

public class CustomData {

	private File file;
	private Map<String, Object> customData = new HashMap<>();

	public CustomData(WhatIsThis plugin) {
		file = new File(plugin.getDataFolder(), "custom.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		loadCustomData();
	}

	private void loadCustomData() {
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection custom = config.getConfigurationSection("custom");
		if (custom == null) {
			return;
		}
		customData = custom.getValues(false);
	}

	private boolean hasCustomData() {
		return !customData.isEmpty();
	}

	public boolean hasCustomName(String name) {
		return hasCustomData() && customData.containsKey(name);
	}

	public String getCustomName(String name) {
		return (String) customData.get(name);
	}
}

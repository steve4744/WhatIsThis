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
				plugin.saveResource("custom.yml", true);
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

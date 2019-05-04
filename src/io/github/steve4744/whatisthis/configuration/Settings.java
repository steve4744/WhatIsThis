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

import org.bukkit.Material;

import io.github.steve4744.whatisthis.WhatIsThis;

public class Settings {

	private final WhatIsThis plugin;

	public Settings(WhatIsThis plugin) {
		this.plugin = plugin;
	}

	public Material getClickItem() {
		Material clickItem = Material.getMaterial(plugin.getConfig().getString("use_right_click.item"));
		return clickItem != null ? clickItem : Material.STICK;
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
}

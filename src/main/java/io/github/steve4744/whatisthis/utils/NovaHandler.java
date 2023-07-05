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
package io.github.steve4744.whatisthis.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import io.github.steve4744.whatisthis.WhatIsThis;
import xyz.xenondevs.nova.api.Nova;
import xyz.xenondevs.nova.api.block.NovaBlock;
import xyz.xenondevs.nova.api.block.NovaBlockRegistry;
import xyz.xenondevs.nova.api.tileentity.TileEntity;
import xyz.xenondevs.nova.api.tileentity.TileEntityManager;

public class NovaHandler {

	private static TileEntityManager manager = Nova.getNova().getTileEntityManager();
	private static NovaBlockRegistry blockRegistry = Nova.getNova().getBlockRegistry();

	public static boolean isNova(Location loc) {
		return manager.getTileEntity(loc) != null;
	}

	public static String getDisplayName(Location loc, String locale) {
		TileEntity tileEntity = manager.getTileEntity(loc);
		return tileEntity.getBlock().getLocalizedName(locale);
	}

	/**
	 * Get the localised name of the Nova block and any other items that it might drop.
	 * This assumes a Nova block always drops itself.
	 *
	 * @param loc
	 * @param locale
	 * @return dropped items
	 */
	public static Map<String, Integer> getNovaItemDrops(Location loc, String locale) {
		Map<String, Integer> drops = new HashMap<>();
		TileEntity tileEntity = manager.getTileEntity(loc);
		drops.put(tileEntity.getBlock().getLocalizedName(locale), 1);
		tileEntity.getDrops(false).forEach(i -> {
			NovaBlock block = blockRegistry.getOrNull(i.getType().toString().toLowerCase());
			String name = block != null ?
					block.getLocalizedName(locale) :
					WhatIsThis.getPlugin().getDataHandler().translateItemName(i.getType().toString(), locale);
			drops.put(name, i.getAmount());
		});
		return drops;
	}
}

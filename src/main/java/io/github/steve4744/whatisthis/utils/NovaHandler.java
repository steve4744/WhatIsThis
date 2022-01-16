/*
 * MIT License

Copyright (c) 2021 steve4744

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

import org.bukkit.Location;
import xyz.xenondevs.nova.api.Nova;
import xyz.xenondevs.nova.api.tileentity.TileEntity;
import xyz.xenondevs.nova.api.tileentity.TileEntityManager;

public class NovaHandler {

	private static TileEntityManager manager = Nova.getNova().getTileEntityManager();

	public static boolean isNova(Location loc) {
		return manager.getTileEntityAt(loc) != null;
	}

	public static String getBlockName(Location loc, String locale) {
		TileEntity tileEntity = manager.getTileEntityAt(loc);
		return tileEntity.getMaterial().getLocalizedName(locale);
	}
}

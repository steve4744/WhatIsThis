package io.github.steve4744.whatisthis.utils;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;

import xyz.xenondevs.nova.tileentity.TileEntity;
import xyz.xenondevs.nova.tileentity.TileEntityManager;

public class UtilsKt {

	private static TileEntityManager manager = TileEntityManager.INSTANCE;

	public static boolean check(Location loc) {
		return manager.getTileEntityAt(loc, false) != null ? true : false;
	}

	public static String getBlockName(Location loc) {
		final char[] delim = {' ', '_'};
		TileEntity tileEntity = manager.getTileEntityAt(loc, false);
		return WordUtils.capitalizeFully(tileEntity.getMaterial().getTypeName(), delim);
	}
}

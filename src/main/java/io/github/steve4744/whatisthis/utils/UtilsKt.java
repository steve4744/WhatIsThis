package io.github.steve4744.whatisthis.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import xyz.xenondevs.nova.tileentity.TileEntity;
import xyz.xenondevs.nova.tileentity.TileEntityManager;

public class UtilsKt {

	private static TileEntityManager tem;

	public static boolean check(Location loc) {
		return tem.getTileEntityAt(loc, false) != null ? true : false;
	}

	public static String getBlockName(Location loc) {
		TileEntity tileEntity = tem.getTileEntityAt(loc, false);
		//debug
		Bukkit.getLogger().info("type name = " + tileEntity.getMaterial().getTypeName());
		Bukkit.getLogger().info("localised name = " + tileEntity.getMaterial().getLocalizedName());
		Bukkit.getLogger().info("tostring = " + tileEntity.getMaterial().toString());
		Bukkit.getLogger().info(" entity tostring = " + tileEntity.toString());
		return tileEntity.getMaterial().getTypeName();
	}
}

package io.github.steve4744.whatisthis;

import org.bukkit.Material;

public class Utils {

	public static boolean isAir(Material material) {
		return material == Material.AIR || material == Material.CAVE_AIR || material == Material.VOID_AIR;
	} 

}

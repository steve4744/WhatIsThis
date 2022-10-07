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
package io.github.steve4744.whatisthis.utils;

import org.bukkit.entity.Entity;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;

public class MythicMobsHandler {

	private static BukkitAPIHelper api = MythicBukkit.inst().getAPIHelper();

	public static boolean isMythicMobs(Entity entity) {
		return api.isMythicMob(entity);
	}

	public static String getEntityDisplayName(Entity entity) {
		ActiveMob activeMob = api.getMythicMobInstance(entity);
		return activeMob != null ? activeMob.getDisplayName() : "";
	}

	public static double getEntityHealth(Entity entity) {
		ActiveMob activeMob = api.getMythicMobInstance(entity);
		return activeMob.getEntity().getHealth();
	}

	public static double getEntityMaxHealth(Entity entity) {
		ActiveMob activeMob = api.getMythicMobInstance(entity);
		return activeMob.getEntity().getMaxHealth();
	}

	public static double getEntityHealthNormalised(Entity entity) {
		ActiveMob activeMob = api.getMythicMobInstance(entity);
		return activeMob.getEntity().getHealth() / activeMob.getEntity().getMaxHealth();
	}
}

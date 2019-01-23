package io.github.steve4744.whatisthis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class WhatIsThisListener implements Listener {

	@EventHandler
	public void onQueryBlock(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand().equals(EquipmentSlot.OFF_HAND)) {
			return;
		}
		Player player = event.getPlayer();
		if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
			return;
		}
		
		Block block = event.getClickedBlock();
		Bukkit.getLogger().info("DEBUG: " + block.getDrops());
		WhatIsThis.getScoreboardManager().showTarget(player, block);
	}
}

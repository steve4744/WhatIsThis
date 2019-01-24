package io.github.steve4744.whatisthis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class WhatIsThisCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Command can only be run by a player");
			return true;	
		}
		Player player = (Player) sender;

		if (!player.hasPermission("whatisthis.use")) {
			player.sendMessage("You do not have permission to run WhatIsThis");
			return true;
		}
		//get the block the player is looking at
		BlockIterator iter = new BlockIterator(player, 10);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR)
                continue;
            break;
        }

        WhatIsThis.getScoreboardManager().showTarget(player, lastBlock);
		return false;
	}

}

package io.github.steve4744.whatisthis;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class WhatIsThisCommand implements CommandExecutor {

	private final String version;
	private final WhatIsThis plugin;
	
	public WhatIsThisCommand(String version, WhatIsThis plugin) {
		this.version = version;
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String infoMessage = ChatColor.GREEN + "[WhatIsThis] " + ChatColor.WHITE + "Version " + version + " : plugin by "+ ChatColor.AQUA + "steve4744";
		if (!(sender instanceof Player)) {
			sender.sendMessage(infoMessage);
			return true;	
		}

		Player player = (Player) sender;

		if (!player.hasPermission("whatisthis.use")) {
			player.sendMessage("You do not have permission to run WhatIsThis");
			return true;
		}
		if (args.length > 0) {
			player.sendMessage(infoMessage);
			return true;
		}
		//get the block the player is looking at
		BlockIterator iter = new BlockIterator(player, 10);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (Utils.isAir(lastBlock.getType()))
                continue;
            break;
        }

        plugin.getScoreboardManager().showTarget(player, lastBlock);
		return false;
	} 

}

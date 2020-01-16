package io.github.steve4744.whatisthis.display;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ActionBar {

	private TextComponent tc;

	public ActionBar(String message, String colour) {
		tc = new TextComponent(message);
		tc.setColor(ChatColor.valueOf(colour));
	}

	public void sendBar(Player player) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, tc);
	}

}

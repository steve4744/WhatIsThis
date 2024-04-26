package io.github.steve4744.whatisthis.display;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class ActionBar {

	private BaseComponent bc;

	public ActionBar(String message) {
		bc = TextComponent.fromLegacy(message);
	}

	public void sendBar(Player player) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, bc);
	}

}

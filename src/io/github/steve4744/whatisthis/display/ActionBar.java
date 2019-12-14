package io.github.steve4744.whatisthis.display;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.ChatMessageType;
import net.minecraft.server.v1_15_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;

public class ActionBar {

	private PacketPlayOutChat packet;

	public ActionBar(String message) {
		PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + message + "\"}"), ChatMessageType.GAME_INFO);
		this.packet = packet;
	}

	public void sendBar(Player player) {
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}

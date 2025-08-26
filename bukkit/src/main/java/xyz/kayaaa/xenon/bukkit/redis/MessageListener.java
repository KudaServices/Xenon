package xyz.kayaaa.xenon.bukkit.redis;

import xyz.kayaaa.xenon.bukkit.tools.spigot.ServerUtils;
import xyz.kayaaa.xenon.shared.redis.listener.PacketListener;
import xyz.kayaaa.xenon.shared.redis.packets.misc.MessagePacket;

import java.util.function.Consumer;

public class MessageListener extends PacketListener<MessagePacket> {

    @Override
    public void listen(MessagePacket packet) {
        ServerUtils.sendMessage(packet.getMessage());
    }

}

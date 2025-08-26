package xyz.kayaaa.xenon.bukkit.redis;

import xyz.kayaaa.xenon.bukkit.tools.spigot.ServerUtils;
import xyz.kayaaa.xenon.shared.redis.packets.MessagePacket;

import java.util.function.Consumer;

public class MessageListener implements Consumer<MessagePacket> {

    @Override
    public void accept(MessagePacket messagePacket) {
        ServerUtils.sendMessage(messagePacket.getMessage());
    }

}

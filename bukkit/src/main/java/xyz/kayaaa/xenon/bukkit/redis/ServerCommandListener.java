package xyz.kayaaa.xenon.bukkit.redis;

import org.bukkit.Bukkit;
import org.bukkit.permissions.ServerOperator;
import xyz.kayaaa.xenon.bukkit.XenonPlugin;
import xyz.kayaaa.xenon.bukkit.tools.spigot.ServerUtils;
import xyz.kayaaa.xenon.bukkit.tools.spigot.TaskUtil;
import xyz.kayaaa.xenon.shared.profile.Profile;
import xyz.kayaaa.xenon.shared.redis.listener.PacketListener;
import xyz.kayaaa.xenon.shared.redis.packets.server.ServerCommandPacket;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.ProfileService;

import java.util.function.Consumer;

public class ServerCommandListener extends PacketListener<ServerCommandPacket> {

    @Override
    public void listen(ServerCommandPacket packet) {
        String server = packet.getServer();
        if (server == null || server.equalsIgnoreCase(XenonPlugin.getInstance().getShared().getServer().getName())) {
            ServerUtils.sendMessage( "&b[Xenon] &fA command packet has been sent with command \"/" + packet.getCommand() + "\", it will execute in a second!", ServerOperator::isOp);
            TaskUtil.runTaskLater(() -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), packet.getCommand()), 20L);
        }
    }

}

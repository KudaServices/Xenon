package xyz.kayaaa.xenon.bukkit.redis;

import org.bukkit.Bukkit;
import org.bukkit.permissions.ServerOperator;
import xyz.kayaaa.xenon.bukkit.XenonPlugin;
import xyz.kayaaa.xenon.bukkit.tools.spigot.ServerUtils;
import xyz.kayaaa.xenon.bukkit.tools.spigot.TaskUtil;
import xyz.kayaaa.xenon.shared.redis.listener.PacketListener;
import xyz.kayaaa.xenon.shared.redis.packets.server.ServerCommandPacket;

public class ServerCommandListener extends PacketListener<ServerCommandPacket> {

    @Override
    public void listen(ServerCommandPacket packet) {
        String server = packet.getServer();
        if (server == null || server.equalsIgnoreCase(XenonPlugin.getInstance().getShared().getServer().getName())) {
            ServerUtils.sendMessage( "&b[Xenon] &fA command packet has been sent" + (server == null ? " globally " : " ") + "with command \"/" + packet.getCommand() + "\", executing in some moments!", ServerOperator::isOp);
            TaskUtil.runTaskLater(() -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), packet.getCommand()), 60L);
        }
    }

}

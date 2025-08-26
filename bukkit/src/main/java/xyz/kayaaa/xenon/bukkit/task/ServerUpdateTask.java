package xyz.kayaaa.xenon.bukkit.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.kayaaa.xenon.bukkit.XenonPlugin;
import xyz.kayaaa.xenon.bukkit.tools.spigot.TaskUtil;
import xyz.kayaaa.xenon.shared.redis.packets.server.ServerUpdatePacket;
import xyz.kayaaa.xenon.shared.server.Server;

public class ServerUpdateTask extends BukkitRunnable {

    public ServerUpdateTask() {
        TaskUtil.runTaskTimerAsynchronously(this, 0, 20 * 60);
    }

    @Override
    public void run() {
        Server server = XenonPlugin.getInstance().getShared().getServer();
        server.setWhitelisted(Bukkit.hasWhitelist());
        server.setPlayers(Bukkit.getOnlinePlayers().size());
        server.setMax(Bukkit.getMaxPlayers());
        server.setOnline(true);
        XenonPlugin.getInstance().getShared().getRedis().sendPacket(new ServerUpdatePacket(server.getName(), server.getType().name(), server.isOnline(), server.isWhitelisted(), false, server.getPlayers(), server.getMax()));
    }
}

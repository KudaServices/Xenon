package rip.kuda.xenon.bukkit.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kuda.xenon.bukkit.XenonPlugin;
import rip.kuda.xenon.bukkit.tools.spigot.TaskUtil;
import rip.kuda.xenon.shared.redis.packets.server.ServerUpdatePacket;
import rip.kuda.xenon.shared.server.Server;

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

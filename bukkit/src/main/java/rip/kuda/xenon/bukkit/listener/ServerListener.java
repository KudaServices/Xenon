package rip.kuda.xenon.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import rip.kuda.xenon.bukkit.XenonPlugin;
import rip.kuda.xenon.bukkit.tools.spigot.TaskUtil;
import rip.kuda.xenon.shared.redis.packets.server.ServerUpdatePacket;
import rip.kuda.xenon.shared.server.Server;

public class ServerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        TaskUtil.runTaskLater(() -> {
            Server server = XenonPlugin.getInstance().getShared().getServer();
            server.setWhitelisted(Bukkit.hasWhitelist());
            server.setPlayers(Bukkit.getOnlinePlayers().size());
            server.setMax(Bukkit.getMaxPlayers());
            server.setOnline(true);
            XenonPlugin.getInstance().getShared().getRedis().sendPacket(new ServerUpdatePacket(server.getName(), server.getType().name(), server.isOnline(), server.isWhitelisted(), false, server.getPlayers(), server.getMax()));
        }, 10L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        TaskUtil.runTaskLater(() -> {
            Server server = XenonPlugin.getInstance().getShared().getServer();
            server.setWhitelisted(Bukkit.hasWhitelist());
            server.setPlayers(Bukkit.getOnlinePlayers().size());
            server.setMax(Bukkit.getMaxPlayers());
            server.setOnline(true);
            XenonPlugin.getInstance().getShared().getRedis().sendPacket(new ServerUpdatePacket(server.getName(), server.getType().name(), server.isOnline(), server.isWhitelisted(), false, server.getPlayers(), server.getMax()));
        }, 10L);
    }

}

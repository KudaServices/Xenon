package xyz.kayaaa.xenon.bukkit.redis;

import xyz.kayaaa.xenon.bukkit.tools.spigot.ServerUtils;
import xyz.kayaaa.xenon.shared.profile.Profile;
import xyz.kayaaa.xenon.shared.redis.packets.ServerStatusPacket;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.ProfileService;

import java.util.function.Consumer;

public class ServerStatusListener implements Consumer<ServerStatusPacket> {

    @Override
    public void accept(ServerStatusPacket packet) {
        ServerUtils.sendMessage( "&b[Staff] &f" + packet.getServerName() + " is now " + (packet.isOnline() ? "&aonline" : "&coffline") + (packet.isWhitelisted() ? ", &fbut it is &ewhitelisted." : "."), player -> {
            Profile profile = ServiceContainer.getService(ProfileService.class).find(player.getUniqueId());
            if (profile == null) return false;
            return profile.getCurrentGrant().getData().isStaff();
        });
    }

}

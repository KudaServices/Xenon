package rip.kuda.xenon.bukkit.redis;

import rip.kuda.xenon.bukkit.tools.spigot.ServerUtils;
import rip.kuda.xenon.shared.profile.Profile;
import rip.kuda.xenon.shared.redis.listener.PacketListener;
import rip.kuda.xenon.shared.redis.packets.server.ServerStatusPacket;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ProfileService;

public class ServerStatusListener extends PacketListener<ServerStatusPacket> {

    @Override
    public void listen(ServerStatusPacket packet) {
        ServerUtils.sendMessage( "&b[Staff] &f" + packet.getServerName() + " is now " + (packet.isOnline() ? "&aonline" : "&coffline") + (packet.isWhitelisted() ? ", &fbut it is &ewhitelisted." : "."), player -> {
            Profile profile = ServiceContainer.getService(ProfileService.class).find(player.getUniqueId());
            if (profile == null) return false;
            return profile.getCurrentGrant().getData().isStaff() || player.isOp();
        });
    }

}

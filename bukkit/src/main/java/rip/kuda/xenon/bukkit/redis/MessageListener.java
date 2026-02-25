package rip.kuda.xenon.bukkit.redis;

import rip.kuda.xenon.bukkit.XenonPlugin;
import rip.kuda.xenon.bukkit.tools.spigot.ServerUtils;
import rip.kuda.xenon.shared.profile.Profile;
import rip.kuda.xenon.shared.redis.listener.PacketListener;
import rip.kuda.xenon.shared.redis.packets.misc.MessagePacket;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ProfileService;

public class MessageListener extends PacketListener<MessagePacket> {

    @Override
    public void listen(MessagePacket packet) {
        String server = packet.getServer();
        if (server == null || server.equalsIgnoreCase(XenonPlugin.getInstance().getShared().getServer().getName())) {
            ServerUtils.sendMessage(packet.getMessage(), player -> {
                Profile profile = ServiceContainer.getService(ProfileService.class).find(player.getUniqueId());
                if (profile == null) return false;
                if (!packet.isStaffOnly()) return true;
                return profile.getCurrentGrant().getData().isStaff() || player.isOp();
            });
        }
    }

}

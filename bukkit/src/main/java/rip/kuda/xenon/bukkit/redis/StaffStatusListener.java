package rip.kuda.xenon.bukkit.redis;

import rip.kuda.xenon.bukkit.tools.spigot.ServerUtils;
import rip.kuda.xenon.shared.profile.Profile;
import rip.kuda.xenon.shared.redis.listener.PacketListener;
import rip.kuda.xenon.shared.redis.packets.staff.StaffStatusPacket;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ProfileService;

public class StaffStatusListener extends PacketListener<StaffStatusPacket> {
    @Override
    public void listen(StaffStatusPacket packet) {
        Profile staff = ServiceContainer.getService(ProfileService.class).find(packet.getStaffUUID());
        String color = packet.isJoined() ? "&a" : "&c";
        String joined = color + (packet.isJoined() ? "joined " : "left ");
        ServerUtils.sendMessage( "&b[Staff] &f" + staff.getCurrentGrant().getData().getColor() + staff.getName() + color + " has " + joined + packet.getServerName() + ".", player -> {
            Profile profile = ServiceContainer.getService(ProfileService.class).find(player.getUniqueId());
            if (profile == null) return false;
            return profile.getCurrentGrant().getData().isStaff() || player.isOp();
        });
    }
}

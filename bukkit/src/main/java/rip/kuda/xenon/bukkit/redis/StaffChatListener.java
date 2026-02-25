package rip.kuda.xenon.bukkit.redis;

import rip.kuda.xenon.bukkit.tools.spigot.ServerUtils;
import rip.kuda.xenon.shared.profile.Profile;
import rip.kuda.xenon.shared.redis.listener.PacketListener;
import rip.kuda.xenon.shared.redis.packets.staff.StaffChatPacket;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ProfileService;
import rip.kuda.xenon.shared.tools.string.CC;

public class StaffChatListener extends PacketListener<StaffChatPacket> {
    @Override
    public void listen(StaffChatPacket packet) {
        Profile staff = ServiceContainer.getService(ProfileService.class).find(packet.getStaffUUID());
        ServerUtils.sendMessage( "&b[StaffChat] &f" + staff.getCurrentGrant().getData().getColor() + staff.getName() + " &7[" + packet.getServer() + "]&7: &f" + CC.removeColors(CC.translate(packet.getMessage())),player -> {
            Profile profile = ServiceContainer.getService(ProfileService.class).find(player.getUniqueId());
            if (profile == null) return false;
            return profile.getCurrentGrant().getData().isStaff() || player.isOp();
        });
    }
}

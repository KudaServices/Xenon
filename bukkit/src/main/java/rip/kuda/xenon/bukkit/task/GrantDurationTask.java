package rip.kuda.xenon.bukkit.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kuda.xenon.bukkit.tools.spigot.TaskUtil;
import rip.kuda.xenon.shared.XenonConstants;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ProfileService;
import rip.kuda.xenon.shared.tools.string.CC;

public class GrantDurationTask extends BukkitRunnable {

    public GrantDurationTask() {
        TaskUtil.runTaskTimerAsynchronously(this, 0, 20);
    }

    @Override
    public void run() {
        ServiceContainer.getService(ProfileService.class).getProfiles().forEach(profile -> {
            profile.getAllGrants().forEach(grant -> {
                if (!grant.isExpired()) return;
                if (grant.isRemoved()) return;
                if (Bukkit.getPlayer(profile.getUUID()) != null && Bukkit.getPlayer(profile.getUUID()).isOnline()) {
                    Bukkit.getPlayer(profile.getUUID()).sendMessage(CC.translate(grant.getData().getExpiryMessage()));
                }
                grant.setRemoved(true);
                grant.setRemovedAt(System.currentTimeMillis());
                grant.setRemovalReason("Expired");
                grant.setRemovedBy(XenonConstants.getConsoleUUID());
            });
        });
    }

}

package rip.kuda.xenon.bukkit.command.punishment.create;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kuda.xenon.bukkit.XenonPlugin;
import rip.kuda.xenon.shared.XenonConstants;
import rip.kuda.xenon.shared.grant.Grant;
import rip.kuda.xenon.shared.profile.Profile;
import rip.kuda.xenon.shared.punishment.Punishment;
import rip.kuda.xenon.shared.punishment.PunishmentType;
import rip.kuda.xenon.shared.redis.packets.punish.PunishmentUpdatePacket;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ProfileService;
import rip.kuda.xenon.shared.service.impl.PunishmentService;
import rip.kuda.xenon.shared.tools.java.TimeUtils;
import rip.kuda.xenon.shared.tools.string.CC;

@CommandAlias("mute")
@CommandPermission("xenon.punish.mute")
public class MuteCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players @times *")
    public void mute(CommandSender sender, @Name("target") OfflinePlayer target, @Name("time") String time, @Optional @Name("reason") @Flags("remaining") String reason) {
        if (target == null) {
            sender.sendMessage(XenonConstants.getPlayerNotFound());
            return;
        }

        Profile profile = ServiceContainer.getService(ProfileService.class).find(target.getUniqueId());
        if (profile == null) {
            sender.sendMessage(XenonConstants.getPlayerNotFound());
            return;
        }

        if (profile.findActivePunishment(PunishmentType.MUTE) != null) {
            sender.sendMessage(XenonConstants.getPlayerAlreadyPunished().replace("<punishment_type>", PunishmentType.MUTE.getAction()));
            return;
        }

        boolean isPermanent = time.equalsIgnoreCase("perm") || time.equalsIgnoreCase("permanent");
        long duration = isPermanent ? -1 : TimeUtils.parseTime(time);
        if (!isPermanent && !TimeUtils.isTime(time)) {
            sender.sendMessage(CC.translate("&cDuration needs to be a valid time unit."));
            return;
        }

        if (!isPermanent && duration < 0 || duration == Long.MAX_VALUE) {
            sender.sendMessage(CC.translate("&cDuration needs to be a valid time."));
            return;
        }
        Grant<Punishment> grant = ServiceContainer.getService(PunishmentService.class).create(sender instanceof Player ? ((Player) sender).getUniqueId() : XenonConstants.getConsoleUUID(), PunishmentType.MUTE, reason, duration);
        profile.addGrant(grant);
        ServiceContainer.getService(ProfileService.class).save(profile);
        XenonPlugin.getInstance().getShared().getRedis().sendPacket(new PunishmentUpdatePacket(sender instanceof Player ? ((Player) sender).getUniqueId() : XenonConstants.getConsoleUUID(), target.getUniqueId(), PunishmentType.MUTE.name(), grant.getTimeCreated(), duration, reason, false, false));
    }
}

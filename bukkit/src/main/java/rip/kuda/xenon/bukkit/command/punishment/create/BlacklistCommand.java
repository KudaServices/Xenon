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

@CommandAlias("blacklist|banip|ipban|bl")
@CommandPermission("xenon.punish.blacklist")
public class BlacklistCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players *")
    public void blacklist(CommandSender sender, @Name("target") OfflinePlayer target, @Optional @Name("reason") @Flags("remaining") String reason) {
        if (target == null) {
            sender.sendMessage(XenonConstants.getPlayerNotFound());
            return;
        }

        Profile profile = ServiceContainer.getService(ProfileService.class).find(target.getUniqueId());
        if (profile == null) {
            sender.sendMessage(XenonConstants.getPlayerNotFound());
            return;
        }

        if (profile.findActivePunishment(PunishmentType.BLACKLIST) != null || profile.findActivePunishment(PunishmentType.BAN) != null) {
            sender.sendMessage(XenonConstants.getPlayerAlreadyPunished().replace("<punishment_type>", PunishmentType.BAN.getAction()));
            return;
        }

        Grant<Punishment> grant = ServiceContainer.getService(PunishmentService.class).create(sender instanceof Player ? ((Player) sender).getUniqueId() : XenonConstants.getConsoleUUID(), PunishmentType.BLACKLIST, reason, -1);
        profile.addGrant(grant);
        ServiceContainer.getService(ProfileService.class).save(profile);
        XenonPlugin.getInstance().getShared().getRedis().sendPacket(new PunishmentUpdatePacket(sender instanceof Player ? ((Player) sender).getUniqueId() : XenonConstants.getConsoleUUID(), target.getUniqueId(), PunishmentType.BLACKLIST.name(), grant.getTimeCreated(), -1, reason, false, false));
    }
}

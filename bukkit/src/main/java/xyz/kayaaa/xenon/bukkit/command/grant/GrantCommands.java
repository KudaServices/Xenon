package xyz.kayaaa.xenon.bukkit.command.grant;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;
import xyz.kayaaa.xenon.shared.grant.Grant;
import xyz.kayaaa.xenon.shared.profile.Profile;
import xyz.kayaaa.xenon.shared.rank.Rank;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.GrantService;
import xyz.kayaaa.xenon.shared.service.impl.ProfileService;
import xyz.kayaaa.xenon.shared.tools.java.TimeUtils;
import xyz.kayaaa.xenon.shared.tools.string.CC;

@CommandAlias("grant")
@CommandPermission("xenon.grant.create")
public class GrantCommands extends BaseCommand {

    @Default
    @Description("Grants a rank to a player")
    @CommandCompletion("@players @ranks @times *")
    public void grant(Player sender, @Name("target") Player target, @Name("rank") Rank rank, @Name("time") String time, @Optional @Name("reason") @Flags("remaining") String reason) {
        if (target == null) {
            sender.sendMessage(CC.translate("&cPlayer not found. Please recheck their username!"));
            return;
        }

        Profile profile = ServiceContainer.getService(ProfileService.class).find(target.getUniqueId());
        if (profile.hasRank(rank)) {
            sender.sendMessage(CC.translate("&cThis player already has this rank!"));
            return;
        }

        GrantService service = ServiceContainer.getService(GrantService.class);

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

        Grant<Rank> grant = service.createGrant(rank, sender.getUniqueId(), duration, reason == null ? "None" : reason);
        profile.addGrant(grant);

        target.sendMessage(CC.translate("&aYou have been granted " + rank.getColor() + rank.getName() + "&a!"));
    }
}

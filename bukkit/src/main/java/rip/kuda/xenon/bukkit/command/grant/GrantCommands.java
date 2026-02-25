package rip.kuda.xenon.bukkit.command.grant;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kuda.xenon.bukkit.menus.grant.GrantMenu;
import rip.kuda.xenon.bukkit.service.BukkitGrantService;
import rip.kuda.xenon.shared.XenonConstants;
import rip.kuda.xenon.shared.grant.GrantProcedure;
import rip.kuda.xenon.shared.profile.Profile;
import rip.kuda.xenon.shared.rank.Rank;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ProfileService;
import rip.kuda.xenon.shared.tools.java.TimeUtils;
import rip.kuda.xenon.shared.tools.string.CC;

import java.util.UUID;

@CommandAlias("grant")
@CommandPermission("xenon.grant.create")
public class GrantCommands extends BaseCommand {

    @Default
    @Description("Grants a rank to a player")
    @CommandCompletion("@players")
    public void grants(Player sender, @Name("target") OfflinePlayer target) {
        if (target == null) {
            sender.sendMessage(CC.RED + "Player not found. Please recheck their username!");
            return;
        }

        new GrantMenu(target.getUniqueId()).openMenu(sender);
    }

    @Subcommand("manual")
    @Description("Grants a rank to a player")
    @CommandCompletion("@players @ranks @times *")
    public void grant(CommandSender sender, @Name("target") Player target, @Name("rank") Rank rank, @Name("time") String time, @Optional @Name("reason") @Flags("remaining") String reason) {
        if (target == null) {
            sender.sendMessage(XenonConstants.getPlayerNotFound());
            return;
        }

        Profile profile = ServiceContainer.getService(ProfileService.class).find(target.getUniqueId());
        if (profile.hasRank(rank)) {
            sender.sendMessage(CC.translate("&cThis player already has this rank!"));
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

        UUID author = sender instanceof Player ? ((Player) sender).getUniqueId() : XenonConstants.getConsoleUUID();
        GrantProcedure<Rank> procedure = new GrantProcedure<>(author, target.getUniqueId(), rank, duration, reason);
        ServiceContainer.getService(BukkitGrantService.class).applyGrant(author, target.getUniqueId(), procedure);
    }
}

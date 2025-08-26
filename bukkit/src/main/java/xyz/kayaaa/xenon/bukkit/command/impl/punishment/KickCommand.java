package xyz.kayaaa.xenon.bukkit.command.impl.punishment;

import com.jonahseguin.drink.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kayaaa.xenon.bukkit.XenonPlugin;
import xyz.kayaaa.xenon.bukkit.command.CommandBase;
import xyz.kayaaa.xenon.shared.XenonConstants;
import xyz.kayaaa.xenon.shared.profile.Profile;
import xyz.kayaaa.xenon.shared.punishment.PunishmentType;
import xyz.kayaaa.xenon.shared.redis.packets.punish.PunishmentUpdatePacket;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.ProfileService;
import xyz.kayaaa.xenon.shared.tools.string.CC;

public class KickCommand extends CommandBase {

    public KickCommand() {
        super("kick", false);
    }

    @Command(name = "", desc = "Kicks a player", usage = "<player>  <reason>")
    @Require("xenon.punish.kick")
    public void kick(@Sender CommandSender sender, Player player, @OptArg @Text String reason) {
        if (player == null) {
            sender.sendMessage(CC.translate("&cPlayer not found. Please recheck their username!"));
            return;
        }

        Profile profile = ServiceContainer.getService(ProfileService.class).find(player.getUniqueId());
        if (profile == null) {
            sender.sendMessage(CC.translate("&cPlayer not found. Please recheck their username!"));
            return;
        }

        XenonPlugin.getInstance().getShared().getRedis().sendPacket(new PunishmentUpdatePacket(sender instanceof Player ? ((Player) sender).getUniqueId().toString() : XenonConstants.getConsoleUUID().toString(), player.getUniqueId().toString(), PunishmentType.KICK.name(), System.currentTimeMillis(), -1, reason, false, false));
    }
}

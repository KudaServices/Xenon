package rip.kuda.xenon.bukkit.command.server;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import rip.kuda.xenon.bukkit.tools.spigot.ServerUtils;
import rip.kuda.xenon.shared.profile.Profile;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ProfileService;
import rip.kuda.xenon.shared.tools.string.CC;

@CommandAlias("cc|clearchat")
@CommandPermission("xenon.cmd.clearchat")
public class ClearChatCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players *")
    public void clear(CommandSender sender, @Optional @Default("false") boolean ignoreStaff) {
        sender.sendMessage(CC.GREEN + "Clearing chat...");
        for (int i = 0; i < 500; i++) {
            ServerUtils.sendMessage(" ", player -> {
                Profile profile = ServiceContainer.getService(ProfileService.class).find(player.getUniqueId());
                if (profile == null) return true;
                if (ignoreStaff) return true;
                return !profile.getCurrentGrant().getData().isStaff();
            });
        }
        ServerUtils.sendMessage("&aThe chat messages were cleared!");
    }
}

package xyz.kayaaa.xenon.bukkit.command.server;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import xyz.kayaaa.xenon.bukkit.tools.spigot.ServerUtils;
import xyz.kayaaa.xenon.shared.profile.Profile;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.ProfileService;
import xyz.kayaaa.xenon.shared.tools.string.CC;

@CommandAlias("cc|clearchat")
@CommandPermission("xenon.cmd.broadcast")
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

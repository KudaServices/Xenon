package xyz.kayaaa.xenon.bukkit.command.impl.server;

import com.jonahseguin.drink.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kayaaa.xenon.bukkit.command.CommandBase;
import xyz.kayaaa.xenon.bukkit.tools.spigot.ServerUtils;
import xyz.kayaaa.xenon.shared.profile.Profile;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.ProfileService;
import xyz.kayaaa.xenon.shared.tools.string.CC;

public class ClearChatCommand extends CommandBase {

    public ClearChatCommand() {
        super("clearchat", false, "cc");
    }

    @Command(name = "", desc = "Clears the server chat", usage = "<ignoreStaff>")
    @Require("xenon.cmd.clearchat")
    public void clear(@Sender CommandSender sender, boolean ignoreStaff) {
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

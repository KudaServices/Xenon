package xyz.kayaaa.xenon.bukkit.command.impl.server;

import com.jonahseguin.drink.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kayaaa.xenon.bukkit.XenonPlugin;
import xyz.kayaaa.xenon.bukkit.command.CommandBase;
import xyz.kayaaa.xenon.bukkit.menus.ServersMenu;
import xyz.kayaaa.xenon.shared.redis.packets.misc.MessagePacket;
import xyz.kayaaa.xenon.shared.redis.packets.server.ServerCommandPacket;
import xyz.kayaaa.xenon.shared.server.Server;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.ServerService;
import xyz.kayaaa.xenon.shared.tools.string.CC;

public class ServersCommand extends CommandBase {

    public ServersCommand() {
        super("servers", false, "servermanager", "sm");
    }

    @Command(name = "", desc = "Shows all the registered servers")
    @Require("xenon.cmd.servers")
    public void servers(@Sender CommandSender sender) {
        if (sender instanceof Player) {
            new ServersMenu().openMenu((Player) sender);
        } else {
            sender.sendMessage(CC.translate("&9All servers: "));
            ServiceContainer.getService(ServerService.class).getServers().forEach(server -> sender.sendMessage(CC.translate("&7- &9" + server.getName() + " &7(" + server.getStatus() + " &7- &9" + server.getPlayers() + "/" + server.getMax() + "&7)")));
        }
    }

    @Command(name = "send", desc = "Sends a command to a specified server")
    @Require("xenon.cmd.servers")
    public void servers(@Sender CommandSender sender, Server server, @Text String command) {
        if (server == null) {
            sender.sendMessage(CC.translate("&cThis server was not found."));
            return;
        }

        if (!server.isOnline()) {
            sender.sendMessage(CC.translate("&cThis server cannot recieve commands as it is offline."));
            return;
        }

        XenonPlugin.getInstance().getShared().getRedis().sendPacket(new ServerCommandPacket(server.getName(), command));
        sender.sendMessage(CC.translate("&aSent command to " + server.getName() + ": " + command));
    }

    @Command(name = "sendtoall", desc = "Sends a command to all servers")
    @Require("xenon.cmd.servers")
    public void servers(@Sender CommandSender sender, @Text String command) {
        XenonPlugin.getInstance().getShared().getRedis().sendPacket(new ServerCommandPacket(null, command));
        sender.sendMessage(CC.translate("&aSent command to all servers: " + command));
    }

}

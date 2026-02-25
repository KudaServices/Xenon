package rip.kuda.xenon.bukkit.command.server;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kuda.xenon.bukkit.XenonPlugin;
import rip.kuda.xenon.bukkit.menus.ServersMenu;
import rip.kuda.xenon.shared.redis.packets.server.ServerCommandPacket;
import rip.kuda.xenon.shared.server.Server;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ServerService;
import rip.kuda.xenon.shared.tools.string.CC;

@CommandAlias("servers|servermanager|sm")
@CommandPermission("xenon.cmd.servers")
public class ServersCommand extends BaseCommand {

    @Default
    @Description("Shows all the registered servers")
    public void servers(CommandSender sender) {
        if (sender instanceof Player) {
            new ServersMenu().openMenu((Player) sender);
        } else {
            sender.sendMessage(CC.translate("&9All servers: "));
            ServiceContainer.getService(ServerService.class).getServers().forEach(server ->
                    sender.sendMessage(CC.translate("&7- &9" + server.getName() + " &7(" + server.getStatus() + " &7- &9" + server.getPlayers() + "/" + server.getMax() + "&7)"))
            );
        }
    }

    @Subcommand("send")
    @Description("Sends a command to a specified server")
    @CommandCompletion("@servers")
    @Syntax("<server> <command>")
    public void send(CommandSender sender, Server server, String command) {
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

    @Subcommand("sendtoall")
    @Description("Sends a command to all servers")
    @Syntax("<command>")
    public void sendtoall(CommandSender sender, String command) {
        XenonPlugin.getInstance().getShared().getRedis().sendPacket(new ServerCommandPacket(null, command));
        sender.sendMessage(CC.translate("&aSent command to all servers: " + command));
    }
}
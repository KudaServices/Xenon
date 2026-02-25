package rip.kuda.xenon.bukkit.command.player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;
import rip.kuda.xenon.bukkit.menus.ChatColorMenu;

@CommandAlias("chatcolor|color")
@CommandPermission("xenon.cmd.chatcolor")
public class ChatColorCommand extends BaseCommand {

    @Default
    public void color(Player sender) {
        new ChatColorMenu().openMenu(sender);
    }
}

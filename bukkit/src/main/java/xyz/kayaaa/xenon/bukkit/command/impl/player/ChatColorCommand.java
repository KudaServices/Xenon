package xyz.kayaaa.xenon.bukkit.command.impl.player;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.entity.Player;
import xyz.kayaaa.xenon.bukkit.command.CommandBase;
import xyz.kayaaa.xenon.bukkit.menus.ChatColorMenu;

public class ChatColorCommand extends CommandBase {

    public ChatColorCommand() {
        super("chatcolor", false, "ccolor");
    }

    @Command(name = "", desc = "Sets your chat color", usage = "<color>")
    @Require("xenon.cmd.chatcolor")
    public void color(@Sender Player sender) {
        new ChatColorMenu().openMenu(sender);
    }
}

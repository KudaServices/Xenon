package xyz.kayaaa.xenon.bukkit.command.impl;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.OptArg;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xyz.kayaaa.xenon.bukkit.command.CommandBase;
import xyz.kayaaa.xenon.bukkit.menus.PunishmentsMenu;

public class PunishmentHistoryCommand extends CommandBase {

    public PunishmentHistoryCommand() {
        super("punishments", false, "history", "h", "ph");
    }

    @Command(name = "", desc = "View all punishments of a player", usage = "<target>")
    @Require("xenon.grant.view")
    public void grants(@Sender Player player, @OptArg OfflinePlayer target) {
        if (target == null) {
            new PunishmentsMenu(player.getUniqueId()).openMenu(player);
            return;
        }

        new PunishmentsMenu(target.getUniqueId()).openMenu(player);
    }

}

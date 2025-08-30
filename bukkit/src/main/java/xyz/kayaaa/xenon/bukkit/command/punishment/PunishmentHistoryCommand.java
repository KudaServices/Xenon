package xyz.kayaaa.xenon.bukkit.command.punishment;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xyz.kayaaa.xenon.bukkit.menus.PunishmentsMenu;

@CommandAlias("punishments|history|h|ph")
@CommandPermission("xenon.punish.view")
public class PunishmentHistoryCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void punishments(Player sender, @Optional @Name("target") OfflinePlayer target) {
        if (target == null) {
            new PunishmentsMenu(sender.getUniqueId()).openMenu(sender);
            return;
        }

        new PunishmentsMenu(target.getUniqueId()).openMenu(sender);
    }
}

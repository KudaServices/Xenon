package rip.kuda.xenon.bukkit.menus.grant.buttons;

import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import rip.kuda.xenon.bukkit.XenonPlugin;
import rip.kuda.xenon.bukkit.menus.grant.prompt.ReasonPrompt;
import rip.kuda.xenon.bukkit.service.BukkitGrantService;
import rip.kuda.xenon.bukkit.tools.menu.Button;
import rip.kuda.xenon.bukkit.tools.menu.menus.ConfirmMenu;
import rip.kuda.xenon.bukkit.tools.spigot.ItemBuilder;
import rip.kuda.xenon.shared.grant.GrantProcedure;
import rip.kuda.xenon.shared.rank.Rank;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.tools.string.CC;

import java.util.UUID;

public class ReasonButton extends Button {

    private final GrantProcedure<Rank> procedure;
    private final UUID target;
    private final String reason;

    public ReasonButton(GrantProcedure<Rank> procedure, UUID target, String reason) {
        this.procedure = procedure;
        this.target = target;
        this.reason = reason;
    }

    @Override
    public ItemStack getButtonItem(Player p) {
        return new ItemBuilder(Material.PAPER)
                .name("&e" + reason)
                .lore("", "&eClick to set the reason!")
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (reason.equalsIgnoreCase("Custom")) {
            player.closeInventory();
            ConversationFactory factory = new ConversationFactory(XenonPlugin.getInstance())
                    .withFirstPrompt(new ReasonPrompt(procedure, target))
                    .withLocalEcho(false)
                    .withTimeout(60)
                    .thatExcludesNonPlayersWithMessage("Only players can use this!");

            Conversation conv = factory.buildConversation(player);
            conv.begin();
        } else {
            procedure.setReason(reason);
            Button.playSuccess(player);
            new ConfirmMenu(
                    "&9Confirm grant?",
                    result -> {
                        if (result) {
                            ServiceContainer.getService(BukkitGrantService.class).applyGrant(player.getUniqueId(), target, procedure);
                            Button.playSuccess(player);
                        } else {
                            player.sendMessage(CC.RED + "You cancelled the grant procedure.");
                            Button.playFail(player);
                        }
                    },
                    true,
                    new ProcedureButton(procedure)
            ).openMenu(player);
        }
    }
}

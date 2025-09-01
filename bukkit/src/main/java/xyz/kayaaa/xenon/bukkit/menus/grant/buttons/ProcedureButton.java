package xyz.kayaaa.xenon.bukkit.menus.grant.buttons;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.kayaaa.xenon.bukkit.tools.menu.Button;
import xyz.kayaaa.xenon.bukkit.tools.spigot.ItemBuilder;
import xyz.kayaaa.xenon.shared.grant.GrantProcedure;
import xyz.kayaaa.xenon.shared.profile.Profile;
import xyz.kayaaa.xenon.shared.rank.Rank;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.ProfileService;
import xyz.kayaaa.xenon.shared.tools.java.TimeUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProcedureButton extends Button {

    private final GrantProcedure<Rank> procedure;

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder builder = new ItemBuilder(Material.BOOK);
        builder.name("&9Grant Procedure");
        Profile target = ServiceContainer.getService(ProfileService.class).find(procedure.getTarget());
        Profile author = ServiceContainer.getService(ProfileService.class).find(procedure.getAuthor());
        if (target == null || author == null) {
            return builder.build();
        }

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&fTarget: " + target.getCurrentGrant().getData().getColor() + target.getName());
        lore.add("&fAuthor: " + author.getCurrentGrant().getData().getColor() + author.getName());
        lore.add("");
        lore.add("&fRank: " + procedure.getData().getColor() + procedure.getData().getName());
        lore.add("&fDuration: &9" + (procedure.getDuration() == -1 ? "Permanent" : TimeUtils.formatTimeShort(procedure.getDuration())));
        lore.add("&fReason: &9" + procedure.getReason());
        builder.lore(lore);
        return builder.build();
    }
}

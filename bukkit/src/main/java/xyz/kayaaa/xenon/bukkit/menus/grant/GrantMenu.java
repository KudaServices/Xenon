package xyz.kayaaa.xenon.bukkit.menus.grant;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.kayaaa.xenon.bukkit.menus.grant.buttons.DurationButton;
import xyz.kayaaa.xenon.bukkit.menus.grant.buttons.RankButton;
import xyz.kayaaa.xenon.bukkit.menus.grant.buttons.ReasonButton;
import xyz.kayaaa.xenon.bukkit.tools.menu.Button;
import xyz.kayaaa.xenon.bukkit.tools.menu.pagination.PaginatedMenu;
import xyz.kayaaa.xenon.shared.grant.GrantProcedure;
import xyz.kayaaa.xenon.shared.rank.Rank;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.RankService;

import java.util.*;
import java.util.stream.Collectors;

public class GrantMenu extends PaginatedMenu {

    public enum Stage { RANK, DURATION, REASON }

    @Setter
    private GrantProcedure<Rank> procedure;
    private final UUID target;
    private final Stage stage;

    public GrantMenu(UUID target) {
        this(target, new GrantProcedure<>(), Stage.RANK);
    }

    @Override
    public int getSize() {
        if (stage == Stage.RANK) return 45;

        return 27;
    }

    public GrantMenu(UUID target, GrantProcedure<Rank> procedure, Stage stage) {
        this.target = target;
        this.procedure = procedure;
        this.procedure.setTarget(target);
        this.stage = stage;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        switch (stage) {
            case RANK:
                return "&9Grant a rank to " + Bukkit.getOfflinePlayer(target).getName();
            case DURATION:
                return "&9Select duration";
            case REASON:
                return "&9Select reason";
            default:
                return "&9Grant";
        }
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        if (procedure.getAuthor() == null) procedure.setAuthor(player.getUniqueId());

        switch (stage) {
            case RANK:
                Comparator<Rank> comparator = Comparator.comparingInt(Rank::getWeight).reversed();
                for (Rank rank : ServiceContainer.getService(RankService.class).getRanks().stream().filter(rank -> !rank.isDefaultRank()).sorted(comparator).collect(Collectors.toList())) {
                    buttons.put(buttons.size(), new RankButton(procedure, target, rank));
                }
                break;

            case DURATION:
                List<String> durations = Arrays.asList("30d", "14d", "7d", "1d", "10m", "Permanent", "Custom");
                for (String d : durations) {
                    buttons.put(buttons.size(), new DurationButton(procedure, target, d));
                }
                break;

            case REASON:
                List<String> reasons = Arrays.asList("Promotion", "Event winner", "Purchased", "Giveaway", "Custom");
                for (String r : reasons) {
                    buttons.put(buttons.size(), new ReasonButton(procedure, target, r));
                }
                break;
        }
        return buttons;
    }
}

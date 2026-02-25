package rip.kuda.xenon.bukkit.command.player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;
import rip.kuda.xenon.shared.chat.ChatChannel;
import rip.kuda.xenon.shared.profile.Profile;
import rip.kuda.xenon.shared.service.ServiceContainer;
import rip.kuda.xenon.shared.service.impl.ProfileService;
import rip.kuda.xenon.shared.tools.string.CC;

@CommandAlias("sc|staffchat")
@CommandPermission("xenon.staffchat")
public class StaffChatCommand extends BaseCommand {

    @Default
    @Description("Enters/leaves staff chat")
    public void onStaffChat(Player sender) {
        Profile profile = ServiceContainer.getService(ProfileService.class).find(sender.getUniqueId());
        if (profile == null) return;

        profile.setChannel(profile.getChannel() == ChatChannel.DEFAULT ? ChatChannel.STAFF : ChatChannel.DEFAULT);
        sender.sendMessage(CC.translate(profile.getChannel() == ChatChannel.STAFF ? "&aYou have joined staff chat!" : "&cYou have left staff chat!"));
    }
}
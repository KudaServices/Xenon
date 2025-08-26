package xyz.kayaaa.xenon.bukkit.command.impl;

import com.jonahseguin.drink.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kayaaa.xenon.bukkit.XenonPlugin;
import xyz.kayaaa.xenon.bukkit.command.CommandBase;
import xyz.kayaaa.xenon.shared.redis.packets.MessagePacket;

public class BroadcastCommand extends CommandBase {

    public BroadcastCommand() {
        super("broadcast", false, "bc", "alert");
    }

    @Command(name = "", desc = "Broadcasts a message", usage = "<message>")
    @Require("xenon.cmd.broadcast")
    public void broadcast(@Sender CommandSender sender, @Text String message) {
        XenonPlugin.getInstance().getShared().getRedis().sendPacket(new MessagePacket(message));
    }

}

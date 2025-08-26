package xyz.kayaaa.xenon.shared.redis.packets.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.kayaaa.xenon.shared.redis.RedisPacket;

@NoArgsConstructor @AllArgsConstructor @Getter
public class ServerCommandPacket implements RedisPacket {

    private String server;
    private String command;

    @Override
    public String getID() {
        return "SERVER_COMMAND";
    }
}

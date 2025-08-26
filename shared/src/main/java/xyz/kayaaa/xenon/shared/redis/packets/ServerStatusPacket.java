package xyz.kayaaa.xenon.shared.redis.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.kayaaa.xenon.shared.redis.RedisPacket;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServerStatusPacket implements RedisPacket {
    private String serverName;
    private boolean online;
    private boolean whitelisted;

    @Override
    public String getID() {
        return "SERVER_STATUS";
    }
}

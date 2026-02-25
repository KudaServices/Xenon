package rip.kuda.xenon.shared.redis.packets.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rip.kuda.xenon.shared.redis.RedisPacket;

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

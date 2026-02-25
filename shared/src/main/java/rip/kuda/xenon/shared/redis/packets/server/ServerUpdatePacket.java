package rip.kuda.xenon.shared.redis.packets.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rip.kuda.xenon.shared.redis.RedisPacket;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServerUpdatePacket implements RedisPacket {

    private String serverName;
    private String serverType;
    private boolean online;
    private boolean whitelisted;
    private boolean sendStatus;
    private int players;
    private int max;

    @Override
    public String getID() {
        return "SERVER_UPDATE";
    }
}
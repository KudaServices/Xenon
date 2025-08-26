package xyz.kayaaa.xenon.shared.redis.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.kayaaa.xenon.shared.redis.RedisPacket;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Getter
public class PunishmentUpdatePacket implements RedisPacket {

    private String author;
    private String target;
    private String punishmentType;
    private long timeCreated;
    private long duration;
    private String reason;
    private boolean removed;
    private boolean silent;

    public String getReason() {
        return reason == null ? "Unspecified" : reason;
    }

    @Override
    public String getID() {
        return "PUNISHMENT_UPDATE";
    }
}

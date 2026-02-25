package rip.kuda.xenon.shared.redis.listener;

import rip.kuda.xenon.shared.redis.RedisPacket;

import java.util.function.Consumer;

public abstract class PacketListener<T extends RedisPacket> implements Consumer<T> {

    @Override
    public void accept(T t) {
        this.listen(t);
    }

    public abstract void listen(T packet);

}

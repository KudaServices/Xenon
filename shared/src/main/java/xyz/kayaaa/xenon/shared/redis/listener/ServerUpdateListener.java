package xyz.kayaaa.xenon.shared.redis.listener;

import xyz.kayaaa.xenon.shared.XenonShared;
import xyz.kayaaa.xenon.shared.redis.packets.server.ServerUpdatePacket;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.ServerService;

import java.util.function.Consumer;

public class ServerUpdateListener extends PacketListener<ServerUpdatePacket> {

    @Override
    public void listen(ServerUpdatePacket packet) {
        String serverName = packet.getServerName();
        String serverType = packet.getServerType();
        if (serverName == null || serverType == null) {
            XenonShared.getInstance().getLogger().warn("Received invalid server update packet!");
            return;
        }

        ServiceContainer.getService(ServerService.class).updateServer(packet);
    }

}

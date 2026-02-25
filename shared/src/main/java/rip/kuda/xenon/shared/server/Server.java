package rip.kuda.xenon.shared.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor @Getter @Setter
public class Server {

    private final String name;
    private final ServerType type;

    private boolean online = false;
    private boolean whitelisted = false;
    private int players = 0;
    private int max = 0;

    public String getStatus() {
        return this.online && this.whitelisted ? "&eWhitelisted" : this.online ? "&aOnline" : "&cOffline";
    }

}

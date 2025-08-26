package xyz.kayaaa.xenon.bukkit.provider;

import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import com.jonahseguin.drink.parametric.DrinkProvider;
import xyz.kayaaa.xenon.shared.server.Server;
import xyz.kayaaa.xenon.shared.service.ServiceContainer;
import xyz.kayaaa.xenon.shared.service.impl.ServerService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServerProvider extends DrinkProvider<Server> {

    @Override
    public boolean doesConsumeArgument() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean allowNullArgument() {
        return false;
    }

    @Nullable
    @Override
    public Server defaultNullValue() {
        return null;
    }

    @Nullable
    @Override
    public Server provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        String name = arg.get();
        Optional<Server> server = ServiceContainer.getService(ServerService.class).find(name);
        if (server == null || !server.isPresent()) {
            throw new CommandExitMessage("No server with name '" + name + "'.");
        }
        return server.get();
    }

    @Override
    public String argumentDescription() {
        return "server";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        final String finalPrefix = prefix.toLowerCase();
        return ServiceContainer.getService(ServerService.class).getServers().stream().map(Server::getName).filter(s -> finalPrefix.isEmpty() || s.toLowerCase().startsWith(finalPrefix)).collect(Collectors.toList());
    }

}

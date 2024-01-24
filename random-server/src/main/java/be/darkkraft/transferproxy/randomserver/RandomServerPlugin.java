package be.darkkraft.transferproxy.randomserver;

import be.darkkraft.transferproxy.api.event.EventType;
import be.darkkraft.transferproxy.api.plugin.Plugin;
import be.darkkraft.transferproxy.randomserver.config.ServerConfiguration;
import be.darkkraft.transferproxy.randomserver.data.Server;
import be.darkkraft.transferproxy.randomserver.listener.RandomReadyListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RandomServerPlugin implements Plugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomServerPlugin.class);

    @Override
    public void onEnable() {
        final ServerConfiguration config = this.makeConfiguration(ServerConfiguration.class);

        final Server[] servers = config.getServers()
                .stream()
                .filter(string -> string.indexOf(':') != -1)
                .map(s -> s.split(":"))
                .map(s -> new Server(s[0], parseInt(s[1])))
                .filter(s -> s.port() != -1)
                .toArray(Server[]::new);

        LOGGER.info("Servers found: {}", servers.length);
        this.getProxy().getModuleManager().setListener(EventType.READY, new RandomReadyListener(servers));
    }

    private static int parseInt(final @NotNull String string) {
        try {
            return Integer.parseInt(string);
        } catch (final Exception exception) {
            LOGGER.info("Invalid server port '{}'", string, exception);
            return -1;
        }
    }

}
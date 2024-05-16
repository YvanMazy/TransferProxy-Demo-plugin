package net.transferproxy.oneserver;

import net.transferproxy.api.event.EventType;
import net.transferproxy.api.event.listener.ReadyListener;
import net.transferproxy.api.plugin.Plugin;
import net.transferproxy.oneserver.config.ServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OneServerPlugin implements Plugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(OneServerPlugin.class);

    @Override
    public void onEnable() {
        final ServerConfiguration config = this.makeConfiguration(ServerConfiguration.class);

        final String address = config.getServerAddress();
        final int port = config.getServerPort();

        LOGGER.info("Players will be transferred to {}:{}", address, port);
        this.getEventManager().<ReadyListener>addListener(EventType.READY, connection -> {
            connection.storeCookie("minecraft:test", new byte[5_120]);
            connection.transfer(address, port);
        });
    }

}
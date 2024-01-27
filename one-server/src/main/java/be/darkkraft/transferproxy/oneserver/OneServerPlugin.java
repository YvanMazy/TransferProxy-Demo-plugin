package be.darkkraft.transferproxy.oneserver;

import be.darkkraft.transferproxy.api.event.EventType;
import be.darkkraft.transferproxy.api.event.listener.ReadyListener;
import be.darkkraft.transferproxy.api.plugin.Plugin;
import be.darkkraft.transferproxy.oneserver.config.ServerConfiguration;
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
        this.getModuleManager().<ReadyListener>setListener(EventType.READY, connection -> connection.transfer(address, port));
    }

}
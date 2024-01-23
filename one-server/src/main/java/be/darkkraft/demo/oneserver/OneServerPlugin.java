package be.darkkraft.demo.oneserver;

import be.darkkraft.transferproxy.api.event.EventType;
import be.darkkraft.transferproxy.api.event.listener.ReadyListener;
import be.darkkraft.transferproxy.api.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

public final class OneServerPlugin implements Plugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(OneServerPlugin.class);

    private static final Path PATH = Path.of("plugins/server-address.txt");

    private String address;
    private int port;

    @Override
    public void onEnable() {
        this.fetchAddress();
        if (this.address == null) {
            return;
        }
        LOGGER.info("Players will be transferred to {}:{}", this.address, this.port);
        this.getProxy()
                .getModuleManager()
                .<ReadyListener>setListener(EventType.READY, connection -> connection.transfer(this.address, this.port));
    }

    @Override
    public void onDisable() {

    }

    private void fetchAddress() {
        try {
            if (Files.notExists(PATH)) {
                Files.write(PATH, "localhost:35565".getBytes());
            }
            final String raw = Files.readString(PATH);
            final String[] split = raw.split(":");
            if (split.length != 2) {
                LOGGER.error("Invalid address format!");
                return;
            }
            this.address = split[0];
            this.port = Integer.parseInt(split[1]);
        } catch (final Exception exception) {
            LOGGER.error("Failed to load configuration ", exception);
        }
    }

}
package net.transferproxy.randomserver.listener;

import net.transferproxy.api.event.listener.ReadyListener;
import net.transferproxy.api.network.connection.PlayerConnection;
import net.transferproxy.randomserver.data.Server;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomReadyListener implements ReadyListener {

    private final Server[] servers;

    public RandomReadyListener(final @NotNull Server[] servers) {
        this.servers = Objects.requireNonNull(servers, "servers cannot be null");
    }

    @Override
    public void handle(@NotNull final PlayerConnection connection) {
        if (this.servers.length == 0) {
            connection.disconnect(Component.text("No server available", NamedTextColor.RED));
            return;
        }
        final Server server = this.servers[ThreadLocalRandom.current().nextInt(this.servers.length)];
        connection.transfer(server.host(), server.port());
    }

}
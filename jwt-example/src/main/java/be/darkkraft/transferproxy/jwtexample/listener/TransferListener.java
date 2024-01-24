package be.darkkraft.transferproxy.jwtexample.listener;

import be.darkkraft.transferproxy.api.event.listener.ReadyListener;
import be.darkkraft.transferproxy.api.network.connection.PlayerConnection;
import be.darkkraft.transferproxy.jwtexample.config.PluginConfiguration;
import be.darkkraft.transferproxy.jwtexample.util.HashUtil;
import be.darkkraft.transferproxy.jwtexample.wrapper.JWTWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static be.darkkraft.transferproxy.jwtexample.JWTPlugin.COOKIE_KEY;

public class TransferListener implements ReadyListener {

    private final PluginConfiguration config;
    private final JWTWrapper wrapper;

    public TransferListener(final @NotNull PluginConfiguration config, final @NotNull JWTWrapper wrapper) {
        this.config = Objects.requireNonNull(config, "config cannot be null");
        this.wrapper = Objects.requireNonNull(wrapper, "wrapper cannot be null");
    }

    @Override
    public void handle(@NotNull final PlayerConnection connection) {
        // Generate a random salt
        final String salt = HashUtil.generateSalt();
        // Compute the cookie value
        final String input = salt + '\n' + HashUtil.hash(this.config.getHashPassword(), salt);
        // Encode value with JWT
        final String encoded = this.wrapper.encode(COOKIE_KEY, input, null);
        connection.storeCookie(COOKIE_KEY, encoded.getBytes());
        this.transfer(connection);
    }

    private void transfer(final @NotNull PlayerConnection connection) {
        // You can change the transfer rules here
        connection.transfer(this.config.getServerAddress(), this.config.getServerPort());
    }

}
package net.transferproxy.jwtexample.listener;

import net.transferproxy.api.event.listener.PreLoginListener;
import net.transferproxy.api.event.login.PreLoginEvent;
import net.transferproxy.api.network.connection.PlayerConnection;
import net.transferproxy.jwtexample.config.PluginConfiguration;
import net.transferproxy.jwtexample.util.HashUtil;
import net.transferproxy.jwtexample.wrapper.JWTWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.transferproxy.jwtexample.JWTPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginListener implements PreLoginListener {

    private static final int SALT_LENGTH = 24;
    private static final int EXPECTED_LENGTH = 236;

    private final PluginConfiguration config;
    private final JWTWrapper wrapper;
    private final int expectedDecodedLength;

    public LoginListener(final @NotNull PluginConfiguration config, final @NotNull JWTWrapper wrapper) {
        this.config = Objects.requireNonNull(config, "config cannot be null");
        this.wrapper = Objects.requireNonNull(wrapper, "wrapper cannot be null");
        // Pre calculate decoded JWT length
        this.expectedDecodedLength = 24 + 1 + HashUtil.hash(config.getHashPassword(), HashUtil.generateSalt()).length();
    }

    @Override
    public void handle(@NotNull final PreLoginEvent event) {
        final PlayerConnection connection = event.getConnection();
        // If the connection does not come from a transfer, it will not have any cookies.
        if (connection.isFromTransfer()) {
            event.setCanSendSuccessPacket(false);
            this.handle(connection);
        }
    }

    private void handle(final @NotNull PlayerConnection connection) {
        // Fetch auth cookie
        connection.fetchCookie(JWTPlugin.COOKIE_KEY).thenAccept(bytes -> this.handle(connection, bytes));
    }

    private void handle(final @NotNull PlayerConnection connection, final byte[] bytes) {
        // Check if bytes length
        if (bytes != null && bytes.length == EXPECTED_LENGTH) {
            // Decode bytes with JWT
            final String decoded = this.wrapper.decode(new String(bytes));
            // Check decoded length
            if (decoded.length() == this.expectedDecodedLength) {
                // Parse salt and hash
                final String salt = decoded.substring(0, SALT_LENGTH);
                final String hash = decoded.substring(SALT_LENGTH + 1);
                // Compare hash to check validity
                if (HashUtil.hash(this.config.getHashPassword(), salt).equals(hash)) {
                    // Continue login process
                    connection.sendLoginSuccess(connection.getUUID(), connection.getName());
                    return;
                }
            }
        }
        // Any failure results from a disconnection
        connection.disconnect(Component.text("Invalid authentication token", NamedTextColor.RED));
    }

}
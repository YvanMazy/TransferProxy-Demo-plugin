package be.darkkraft.demoplugin.login;

import be.darkkraft.transferproxy.api.login.LoginHandler;
import be.darkkraft.transferproxy.api.network.connection.PlayerConnection;
import be.darkkraft.transferproxy.api.profile.ClientInformation;
import be.darkkraft.transferproxy.api.profile.MainHand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyCustomLoginHandler implements LoginHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCustomLoginHandler.class);

    private final ScheduledExecutorService executor;

    public MyCustomLoginHandler(final ScheduledExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void handle(final PlayerConnection connection) {
        final ClientInformation information = connection.getInformation();

        if (information.mainHand() == MainHand.LEFT) {
            LOGGER.info("Player {} uses left hand as main hand, he's a weird guy", connection.getName());
            // Transfer the player 3 seconds later that it's someone weird
            this.executor.schedule(() -> connection.transfer("localhost", 45565), 3L, TimeUnit.SECONDS);
            return;
        }

        connection.transfer("localhost", 35565);
    }

}
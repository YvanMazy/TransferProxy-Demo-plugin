package be.darkkraft.transferproxy.jwtexample;

import be.darkkraft.transferproxy.api.event.EventType;
import be.darkkraft.transferproxy.api.event.listener.PreLoginListener;
import be.darkkraft.transferproxy.api.event.listener.ReadyListener;
import be.darkkraft.transferproxy.api.plugin.Plugin;
import be.darkkraft.transferproxy.jwtexample.config.PluginConfiguration;
import be.darkkraft.transferproxy.jwtexample.listener.LoginListener;
import be.darkkraft.transferproxy.jwtexample.listener.TransferListener;
import be.darkkraft.transferproxy.jwtexample.wrapper.JWTWrapper;

public final class JWTPlugin implements Plugin {

    public static final String COOKIE_KEY = "transferproxy:auth_example";

    @Override
    public void onEnable() {
        final PluginConfiguration config = this.makeConfiguration(PluginConfiguration.class);

        config.checkDefaultValues();

        final JWTWrapper wrapper = new JWTWrapper(config.getJwtToken());
        this.getEventManager().<PreLoginListener>addListener(EventType.PRE_LOGIN, new LoginListener(config, wrapper));
        this.getEventManager().<ReadyListener>addListener(EventType.READY, new TransferListener(config, wrapper));
    }

}
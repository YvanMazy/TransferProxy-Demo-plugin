package net.transferproxy.jwtexample;

import net.transferproxy.api.event.EventType;
import net.transferproxy.api.event.listener.PreLoginListener;
import net.transferproxy.api.event.listener.ReadyListener;
import net.transferproxy.api.plugin.Plugin;
import net.transferproxy.jwtexample.config.PluginConfiguration;
import net.transferproxy.jwtexample.listener.LoginListener;
import net.transferproxy.jwtexample.listener.TransferListener;
import net.transferproxy.jwtexample.wrapper.JWTWrapper;

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
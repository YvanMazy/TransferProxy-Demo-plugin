package be.darkkraft.demoplugin;

import be.darkkraft.demoplugin.login.MyCustomLoginHandler;
import be.darkkraft.transferproxy.api.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class DemoPlugin implements Plugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoPlugin.class);

    private ScheduledExecutorService executor;

    @Override
    public void onEnable() {
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.getProxy().getModuleManager().setLoginHandler(new MyCustomLoginHandler(this.executor));
        LOGGER.info("DemoPlugin is now enabled and LoginHandler are correctly redefined!");
    }

    @Override
    public void onDisable() {
        this.executor.shutdownNow().forEach(Runnable::run);
        LOGGER.info("DemoPlugin is now disabled!");
    }

}
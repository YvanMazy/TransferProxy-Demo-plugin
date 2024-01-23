package be.darkkraft.transferproxy.demo.basic;

import be.darkkraft.transferproxy.demo.basic.listener.MyCustomReadyListener;
import be.darkkraft.transferproxy.api.event.EventType;
import be.darkkraft.transferproxy.api.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class BasicPlugin implements Plugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicPlugin.class);

    private ScheduledExecutorService executor;

    @Override
    public void onEnable() {
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.getProxy().getModuleManager().setListener(EventType.READY, new MyCustomReadyListener(this.executor));
        LOGGER.info("BasicPlugin is now enabled and LoginHandler are correctly redefined!");
    }

    @Override
    public void onDisable() {
        this.executor.shutdownNow().forEach(Runnable::run);
        LOGGER.info("BasicPlugin is now disabled!");
    }

}
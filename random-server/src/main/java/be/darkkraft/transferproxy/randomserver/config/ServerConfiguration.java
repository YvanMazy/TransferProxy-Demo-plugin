package be.darkkraft.transferproxy.randomserver.config;

import java.util.List;

public final class ServerConfiguration {

    private final List<String> servers;

    public ServerConfiguration() {
        this.servers = List.of("localhost:35565", "localhost:45565");
    }

    public List<String> getServers() {
        return this.servers;
    }

}
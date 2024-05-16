package net.transferproxy.oneserver.config;

public final class ServerConfiguration {

    private final String serverAddress;
    private final int serverPort;

    public ServerConfiguration() {
        this.serverAddress = "localhost";
        this.serverPort = 35565;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public int getServerPort() {
        return this.serverPort;
    }

}
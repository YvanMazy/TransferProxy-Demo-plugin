package be.darkkraft.transferproxy.jwtexample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PluginConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginConfiguration.class);

    private final String jwtToken;
    private final String hashPassword;
    private final String serverAddress;
    private final int serverPort;

    public PluginConfiguration() {
        this.jwtToken = "my-super-secret";
        this.hashPassword = "hash-password";
        this.serverAddress = "localhost";
        this.serverPort = 35565;
    }

    public void checkDefaultValues() {
        if (this.jwtToken.equals("my-super-secret")) {
            LOGGER.warn("You use the JWT token by default, change it if you are in production!");
        }
        if (this.hashPassword.equals("hash-password")) {
            LOGGER.warn("You use the Hash password by default, change it if you are in production!");
        }
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    public String getHashPassword() {
        return this.hashPassword;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public int getServerPort() {
        return this.serverPort;
    }

}
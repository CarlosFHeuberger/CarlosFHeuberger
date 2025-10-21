package com.carlosfheuberger.exchange;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration loader for Exchange server connection settings.
 */
public class ExchangeConfig {
    
    private final String serverUrl;
    private final String username;
    private final String password;
    private final int maxEmails;

    /**
     * Loads configuration from a properties file.
     *
     * @param propertiesFilePath Path to the properties file
     * @throws IOException if the file cannot be read
     */
    public ExchangeConfig(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();
        
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
        }
        
        this.serverUrl = properties.getProperty("exchange.server.url");
        this.username = properties.getProperty("exchange.username");
        this.password = properties.getProperty("exchange.password");
        this.maxEmails = Integer.parseInt(properties.getProperty("exchange.max.emails", "10"));
        
        validateConfig();
    }

    /**
     * Creates a configuration with explicit values.
     *
     * @param serverUrl The Exchange server URL
     * @param username  The username for authentication
     * @param password  The password for authentication
     * @param maxEmails Maximum number of emails to retrieve
     */
    public ExchangeConfig(String serverUrl, String username, String password, int maxEmails) {
        this.serverUrl = serverUrl;
        this.username = username;
        this.password = password;
        this.maxEmails = maxEmails;
        
        validateConfig();
    }

    private void validateConfig() {
        if (serverUrl == null || serverUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Server URL is required");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (maxEmails <= 0) {
            throw new IllegalArgumentException("Max emails must be greater than 0");
        }
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxEmails() {
        return maxEmails;
    }
}

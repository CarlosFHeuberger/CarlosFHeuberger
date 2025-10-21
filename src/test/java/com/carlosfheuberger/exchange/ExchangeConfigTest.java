package com.carlosfheuberger.exchange;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ExchangeConfig class.
 */
public class ExchangeConfigTest {

    @Test
    public void testConfigWithValidValues() {
        String serverUrl = "https://outlook.office365.com/EWS/Exchange.asmx";
        String username = "test@example.com";
        String password = "password123";
        int maxEmails = 10;

        ExchangeConfig config = new ExchangeConfig(serverUrl, username, password, maxEmails);

        assertEquals(serverUrl, config.getServerUrl());
        assertEquals(username, config.getUsername());
        assertEquals(password, config.getPassword());
        assertEquals(maxEmails, config.getMaxEmails());
    }

    @Test
    public void testConfigWithEmptyServerUrl() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ExchangeConfig("", "test@example.com", "password123", 10);
        });
    }

    @Test
    public void testConfigWithEmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ExchangeConfig("https://outlook.office365.com/EWS/Exchange.asmx", "", "password123", 10);
        });
    }

    @Test
    public void testConfigWithEmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ExchangeConfig("https://outlook.office365.com/EWS/Exchange.asmx", "test@example.com", "", 10);
        });
    }

    @Test
    public void testConfigWithInvalidMaxEmails() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ExchangeConfig("https://outlook.office365.com/EWS/Exchange.asmx", "test@example.com", "password123", 0);
        });
    }

    @Test
    public void testConfigWithNegativeMaxEmails() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ExchangeConfig("https://outlook.office365.com/EWS/Exchange.asmx", "test@example.com", "password123", -1);
        });
    }

    @Test
    public void testConfigWithNullServerUrl() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ExchangeConfig(null, "test@example.com", "password123", 10);
        });
    }

    @Test
    public void testConfigWithNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ExchangeConfig("https://outlook.office365.com/EWS/Exchange.asmx", null, "password123", 10);
        });
    }

    @Test
    public void testConfigWithNullPassword() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ExchangeConfig("https://outlook.office365.com/EWS/Exchange.asmx", "test@example.com", null, 10);
        });
    }
}

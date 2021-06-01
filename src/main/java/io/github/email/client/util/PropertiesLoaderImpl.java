package io.github.email.client.util;

import java.util.Properties;

public class PropertiesLoaderImpl implements PropertiesLoader {

    private final Properties properties;

    public PropertiesLoaderImpl(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String getImapHost() {
        return properties.getProperty("mail.imap.host");
    }

    @Override
    public int getImapPort() {
        String port = properties.getProperty("mail.imap.port", "993");
        return Integer.parseInt(port);
    }

    @Override
    public String getPassword() {
        return properties.getProperty("mail.password");
    }

    @Override
    public String getSmtpHost() {
        return properties.getProperty("mail.smtp.host");
    }

    @Override
    public int getSmtpPort() {
        String port = properties.getProperty("mail.smtp.port", "587");
        return Integer.parseInt(port);
    }

    @Override
    public String getUser() {
        return properties.getProperty("mail.user");
    }
}

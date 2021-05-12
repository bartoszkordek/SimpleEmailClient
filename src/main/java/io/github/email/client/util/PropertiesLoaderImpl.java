package io.github.email.client.util;

import java.util.Properties;

public class PropertiesLoaderImpl implements PropertiesLoader {

    private Properties properties;

    public PropertiesLoaderImpl(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String getImapProtocolName() {
        return properties.getProperty("mail.imap", "imap");
    }

    @Override
    public boolean getImapAuth() {
        String authFlag = properties.getProperty("mail.imap.auth", "true");
        return Boolean.parseBoolean(authFlag);
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
    public Class<?> getImapSocketFactoryClass() throws ClassNotFoundException {
        String className = properties.getProperty("mail.imap.socketFactory.class");
        if (className == null) throw new ClassNotFoundException();
        return Class.forName(className);
    }

    @Override
    public boolean getImapSocketFactoryFallback() {
        String fallbackFlag = properties.getProperty("mail.imap.socketFactory.fallback", "false");
        return Boolean.parseBoolean(fallbackFlag);
    }

    @Override
    public int getImapSocketFactoryPort() {
        String port = properties.getProperty("mail.imap.socketFactory.port", "993");
        return Integer.parseInt(port);
    }

    @Override
    public boolean getImapSslCheckServerIdentity() {
        String serverIdentityFlag = properties.getProperty("mail.imap.ssl.checkserveridentity", "false");
        return Boolean.parseBoolean(serverIdentityFlag);
    }

    @Override
    public String getImapSslTrust() {
        return properties.getProperty("mail.imap.ssl.trust", "*");
    }

    @Override
    public String getPassword() {
        return properties.getProperty("mail.password");
    }

    @Override
    public String getSmtpProtocolName() {
        return properties.getProperty("mail.smtp", "smtp");
    }

    @Override
    public boolean getSmtpAuth() {
        String authFlag = properties.getProperty("mail.smtp.auth", "true");
        return Boolean.parseBoolean(authFlag);
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
    public String getSmtpSslTrust() {
        return properties.getProperty("mail.smtp.ssl.trust", "*");
    }

    @Override
    public boolean getSmtpStartTlsEnable() {
        String tlsFlag = properties.getProperty("mail.smtp.starttls.enable", "true");
        return Boolean.parseBoolean(tlsFlag);
    }

    @Override
    public String getUser() {
        return properties.getProperty("mail.user");
    }

    @Override
    public String getTransportProtocol() {
        return properties.getProperty("mail.transport.protocol");
    }
}

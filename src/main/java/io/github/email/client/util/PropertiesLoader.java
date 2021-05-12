package io.github.email.client.util;

public interface PropertiesLoader {
    String getImapProtocolName();

    boolean getImapAuth();

    String getImapHost();

    int getImapPort();

    Class<?> getImapSocketFactoryClass() throws ClassNotFoundException;

    boolean getImapSocketFactoryFallback();

    int getImapSocketFactoryPort();

    boolean getImapSslCheckServerIdentity();

    String getImapSslTrust();

    String getPassword();

    String getSmtpProtocolName();

    boolean getSmtpAuth();

    String getSmtpHost();

    int getSmtpPort();

    String getSmtpSslTrust();

    boolean getSmtpStartTlsEnable();

    String getUser();

    String getTransportProtocol();
}

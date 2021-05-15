package io.github.email.client.util;

public interface PropertiesLoader {
    String getImapHost();

    int getImapPort();

    String getPassword();

    String getSmtpHost();

    int getSmtpPort();

    String getUser();
}

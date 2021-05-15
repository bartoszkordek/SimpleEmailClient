package io.github.email.client.service;

import javax.annotation.Nonnull;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public interface SendApi {
    void sendEmail(@Nonnull Properties configProperties, @Nonnull String[] to, @Nonnull String[] cc, @Nonnull String[] bcc,
                   @Nonnull String subject, @Nonnull String message, @Nonnull File[] attachFiles) throws MessagingException, IOException, NoSuchAlgorithmException, KeyManagementException;
}

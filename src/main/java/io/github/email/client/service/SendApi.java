package io.github.email.client.service;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Properties;

public interface SendApi {
    void sendEmail(@Nonnull Properties configProperties, @Nonnull String[] to, @Nonnull String[] cc, @Nonnull String[] bcc,
                   @Nonnull String subject, @Nonnull String message, @Nonnull File[] attachFiles);
}

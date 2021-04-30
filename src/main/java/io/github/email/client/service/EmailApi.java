package io.github.email.client.service;

import io.github.email.client.imap.MailMetadata;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public interface EmailApi {
    void sendEmail(Properties configProperties, String[] to, String[] cc, String[] bcc,
                   String subject, String message, File[] attachFiles) throws MessagingException, IOException;

    List<MailMetadata> downloadEmails(Properties properties, int limit);
}

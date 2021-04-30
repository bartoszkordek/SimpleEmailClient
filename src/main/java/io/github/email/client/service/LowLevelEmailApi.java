package io.github.email.client.service;

import io.github.email.client.imap.ImapClient;
import io.github.email.client.imap.MailMetadata;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class LowLevelEmailApi implements EmailApi {
    private final ImapClient imapClient;

    public LowLevelEmailApi() {
        this.imapClient = new ImapClient();
    }

    @Override
    public void sendEmail(Properties configProperties, String[] to, String[] cc, String[] bcc,
                          String subject, String message, File[] attachFiles) throws MessagingException, IOException {
        // TODO implement (Grzegorz/Bartosz)
    }

    @Override
    public List<MailMetadata> downloadEmails(Properties properties, int limit) {
        return imapClient.downloadEmails(properties, limit);
    }
}

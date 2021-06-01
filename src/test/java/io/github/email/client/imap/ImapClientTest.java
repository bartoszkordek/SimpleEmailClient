package io.github.email.client.imap;

import io.github.email.client.service.ConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

class ImapClientTest {

    private ImapClient imapClient;
    private ConfigService configService;

    @BeforeEach
    void setUp() {
        imapClient = new ImapClient();
        configService = new ConfigService();
    }

    @Disabled
    @Test
    void shouldDownloadEmails() {
        List<MailMetadata> mailMetadata = imapClient.downloadEmails(configService.getProperties(), 10, null);

        System.out.println(mailMetadata.toString());
    }
}
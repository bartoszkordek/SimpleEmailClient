package io.github.email.client.smtp;

import io.github.email.client.service.ConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

class SmtpClientTest {

    private SmtpClient smtpClient;
    private ConfigService configService;

    @BeforeEach
    void setUp() {
        smtpClient = new SmtpClient(true);
        configService = new ConfigService();
    }

    @Test
    void shouldSendEmail() throws MessagingException, KeyManagementException, NoSuchAlgorithmException, IOException {
        String[] to = new String[]{"aghproject2020@gmail.com"};
        String[] cc = new String[]{};
        String[] bcc = new String[]{};
        String subject = "testSmtp";
        String message = "smtp message";
        File[] attachFiles = new File[0];

        smtpClient.sendEmail(
                configService.getProperties(),
                to,
                cc,
                bcc,
                subject,
                message,
                attachFiles
        );
    }
}
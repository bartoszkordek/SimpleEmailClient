package io.github.email.client.smtp;

import io.github.email.client.service.SendApi;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class SmtpClient implements SendApi {

    @Override
    public void sendEmail(Properties configProperties, String[] to, String[] cc, String[] bcc,
                          String subject, String message, File[] attachFiles) throws MessagingException, IOException {
        // TODO implement (Grzegorz/Bartosz)
    }
}

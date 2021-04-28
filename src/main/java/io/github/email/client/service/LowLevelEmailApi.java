package io.github.email.client.service;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class LowLevelEmailApi implements EmailApi {
    @Override
    public void sendEmail(Properties configProperties, String[] to, String[] cc, String[] bcc,
                          String subject, String message, File[] attachFiles) throws MessagingException, IOException {
        // TODO implement (Grzegorz/Bartosz)
    }

    @Override
    public String[][] downloadEmails(Properties properties) {
        // TODO implement (Marcin)
        return new String[0][];
    }
}

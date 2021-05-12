package io.github.email.client.service;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public interface SendApi {
    void sendEmail(Properties configProperties, String[] to, String[] cc, String[] bcc,
                   String subject, String message, File[] attachFiles) throws MessagingException, IOException, NoSuchAlgorithmException, KeyManagementException;
}

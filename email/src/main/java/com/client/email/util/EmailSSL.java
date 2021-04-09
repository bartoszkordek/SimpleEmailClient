package com.client.email.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class EmailSSL {

    public static void sendEmail(String fromEmail, String personal, String toEmail, String password, String subject,
                                 String body, String filePath){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.poczta.onet.pl"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);
        EmailUtil.sendEmail(session, fromEmail, personal, toEmail,subject, body, filePath);
    }
}

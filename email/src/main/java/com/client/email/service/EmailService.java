package com.client.email.service;

import com.client.email.model.SendEmail;
import com.client.email.util.receive.EmailImap;
import com.client.email.util.send.EmailTLS;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailService {

    public void sendEmailSSL(SendEmail sendEmailRequest) {
        EmailTLS.sendEmail(sendEmailRequest.getFromEmail(), sendEmailRequest.getPersonal(), sendEmailRequest.getToEmail(),
                    sendEmailRequest.getPassword(), sendEmailRequest.getSubject(), sendEmailRequest.getBody());
    }

    public void receiveEmail() throws MessagingException {
        EmailImap.receive();
    }

}

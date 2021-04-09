package com.client.email.service;

import com.client.email.exception.SendEmailException;
import com.client.email.model.SendEmail;
import com.client.email.util.EmailTLS;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmailTLS(SendEmail sendEmailRequest) {
        EmailTLS.sendEmail(sendEmailRequest.getFromEmail(), sendEmailRequest.getPersonal(), sendEmailRequest.getToEmail(),
                    sendEmailRequest.getPassword(), sendEmailRequest.getSubject(), sendEmailRequest.getBody(), sendEmailRequest.getFilePath());
    }

}

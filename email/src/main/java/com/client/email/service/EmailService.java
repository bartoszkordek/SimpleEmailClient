package com.client.email.service;

import com.client.email.util.EmailSSL;
import com.client.email.util.EmailTLS;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmailSSL(String fromEmail, String toEmail, String password){
        EmailTLS.sendEmail(fromEmail,toEmail,password);
    }

}

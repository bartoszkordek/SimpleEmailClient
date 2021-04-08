package com.client.email.controller;

import com.client.email.service.EmailService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @RequestMapping("/test")
    public String test(){
        return "OK";
    }

    @PostMapping("/send/{fromEmail}/{toEmail}/{password}")
    public void sendEmailSSL(@PathVariable("fromEmail") final String fromEmail,
                             @PathVariable("toEmail") final String toEmail,
                             @PathVariable("password") final String password){
        emailService.sendEmailSSL(fromEmail, toEmail, password);
    }


}

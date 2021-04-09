package com.client.email.controller;

import com.client.email.exception.RestException;
import com.client.email.model.SendEmail;
import com.client.email.service.EmailService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

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

    @PostMapping("/send")
    public void sendEmail(@RequestBody SendEmail sendEmailRequest) throws RestException {
        emailService.sendEmailTLS(sendEmailRequest);
    }
}

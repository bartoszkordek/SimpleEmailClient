package com.client.email.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendEmail {

    private String fromEmail;
    private String personal;
    private String toEmail;
    private String password;
    private String subject;
    private String body;
    private String filePath;

    public SendEmail(@JsonProperty("fromEmail") String fromEmail,
                     @JsonProperty("personal") String personal,
                     @JsonProperty("toEmail") String toEmail,
                     @JsonProperty("password") String password,
                     @JsonProperty("subject") String subject,
                     @JsonProperty("body") String body,
                     @JsonProperty("filePath") String filePath

    ){
        this.fromEmail = fromEmail;
        this.personal = personal;
        this.toEmail = toEmail;
        this.password = password;
        this.subject = subject;
        this.body = body;
        this.filePath = filePath;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getPersonal() {
        return personal;
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getFilePath() {
        return filePath;
    }
}

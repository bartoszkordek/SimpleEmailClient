package io.github.email.client.imap;

import java.util.List;

public class MailMetadata {
    private final String date;
    private final String from;
    private final String to;
    private final String cc;
    private final String bcc;
    private final String subject;
    private final String bodyPlain;
    private final String bodyHtml;
    private final List<Attachment> attachments;

    public MailMetadata(String date, String from, String to, String cc, String bcc, String subject,
                        String bodyPlain, String bodyHtml, List<Attachment> attachments) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.bodyPlain = bodyPlain;
        this.bodyHtml = bodyHtml;
        this.attachments = attachments;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getBodyPlain() {
        return bodyPlain;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public String toString() {
        return "MailMetadata{" +
                "date='" + date + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", cc='" + cc + '\'' +
                ", bcc='" + bcc + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}

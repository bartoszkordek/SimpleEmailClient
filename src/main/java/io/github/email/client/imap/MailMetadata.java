package io.github.email.client.imap;

public class MailMetadata {
    private final String date;
    private final String from;
    private final String to;
    private final String cc;
    private final String bcc;
    private final String subject;

    public MailMetadata(String date, String from, String to, String cc, String bcc, String subject) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
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

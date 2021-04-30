package io.github.email.client.imap;

public class MailMetadata {
    private final String date;
    private final String from;
    private final String to;
    private final String cc;
    private final String bcc;
    private final String subject;
    private final String textPlain;
    private final String textHtml;

    public MailMetadata(String date, String from, String to, String cc, String bcc,
                        String subject, String textPlain, String textHtml) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.textPlain = textPlain;
        this.textHtml = textHtml;
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

    public String getTextPlain() {
        return textPlain;
    }

    public String getTextHtml() {
        return textHtml;
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

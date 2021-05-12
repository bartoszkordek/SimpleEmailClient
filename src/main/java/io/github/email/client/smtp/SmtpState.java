package io.github.email.client.smtp;

public enum SmtpState {
    AUTH_LOGIN("auth login"),
    AUTH_PLAIN("auth plain"),
    DATA("data"),
    INIT("init"),
    HELO("helo"),
    EHLO("ehlo"),
    MAIL_FROM("mail from"),
    RCPT_TO("rcpt to"),
    QUIT("quit");

    private String command;

    SmtpState(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}

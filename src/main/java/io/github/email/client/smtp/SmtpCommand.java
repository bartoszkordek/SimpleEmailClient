package io.github.email.client.smtp;

public enum SmtpCommand {
    AUTH_LOGIN("auth login", "auth login"),
    AUTH_PLAIN("auth plain", "auth plain"),
    DATA("data", "data"),
    STARTTLS("starttls", "start tls"),
    HELO("helo", "hello"),
    EHLO("ehlo", "extended hello"),
    MAIL("mail from:", "mail"),
    RCPT("rcpt to:", "recipient"),
    RSET("rset", "reset"),
    VRFY("vrfy", "verify"),
    EXPN("expn", "expand"),
    HELP("help", "help"),
    NOOP("noop", "noop"),
    QUIT("quit", "quit");

    private final String command;
    private final String name;

    SmtpCommand(String command, String name) {
        this.command = command;
        this.name = name;
    }

    @Override
    public String toString() {
        return command;
    }

    public String getName() {
        return name;
    }
}

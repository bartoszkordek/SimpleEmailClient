package io.github.email.client.smtp;

import java.io.IOException;

public interface SmtpUnencryptedCommandSender {

    String connectionEstablished() throws IOException;

    String sendHELOCommand() throws IOException;

    String sendEHLOCommand() throws IOException;
    String sendStartTlsCommand() throws IOException;
}

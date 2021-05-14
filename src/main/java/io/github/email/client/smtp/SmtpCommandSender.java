package io.github.email.client.smtp;

import java.io.File;
import java.io.IOException;

public interface SmtpCommandSender {

    String connectionEstablished() throws IOException;

    String sendHelloCommand() throws IOException;

    String sendEHLOCommand() throws IOException;

    String sendAuthCommands() throws IOException;

    String sendMailFromCommand() throws IOException;

    String sendRcptToCommand(String[] recipients) throws IOException;

    void sendMessageWithoutAttachmentCommand(String message, File footerImage) throws IOException;

    void sendMessageWithAttachmentCommand(String message, File[] attachments, File footerImage) throws IOException;

    String sendDataCommand(
            String[] to,
            String[] cc,
            String[] bcc,
            String subject,
            String message,
            File[] attachFiles
    ) throws IOException;

    String sendQuitCommand() throws IOException;
}

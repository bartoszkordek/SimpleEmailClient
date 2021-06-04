package io.github.email.client.smtp;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

public interface SmtpSSLCommandSender {

    String sendAuthCommands() throws IOException;

    String sendMailFromCommand() throws IOException;

    String sendRcptToCommand(@Nonnull String[] recipients) throws IOException;

    void sendMessageWithoutAttachmentCommand(String message) throws IOException;

    void sendMessageWithAttachmentCommand(String message, File[] attachments) throws IOException;

    String sendDataCommand(
            @Nonnull String[] to,
            @Nonnull String[] cc,
            @Nonnull String[] bcc,
            @Nonnull String subject,
            @Nonnull String message,
            @Nonnull File[] attachFiles
    ) throws IOException;

    String sendQuitCommand() throws IOException;
}

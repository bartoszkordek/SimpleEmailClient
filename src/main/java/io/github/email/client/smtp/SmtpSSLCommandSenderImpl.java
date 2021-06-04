package io.github.email.client.smtp;

import io.github.email.client.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Base64;

public class SmtpSSLCommandSenderImpl implements SmtpSSLCommandSender {

    private final PrintWriter writer;
    private final BufferedReader reader;
    private final PropertiesLoader properties;

    private final Logger logger = LoggerFactory.getLogger(SmtpSSLCommandSenderImpl.class);

    public SmtpSSLCommandSenderImpl(
            PrintWriter writer,
            BufferedReader reader,
            PropertiesLoader propertiesLoader
    ) {
        this.writer = writer;
        this.reader = reader;
        this.properties = propertiesLoader;
    }

    @Override
    public String sendAuthCommands() throws IOException {
        logger.debug("Send AUTH command...");

        final String loginCommand = SmtpCommand.AUTH_LOGIN.toString();
        sendCommand(loginCommand);
        final String serverResponse1 = reader.readLine();
        logger.debug(serverResponse1);
        if (!serverResponse1.startsWith("334"))
            throw new ConnectException("While sending AUTH command error occurred.");

        final String user = properties.getUser();
        final String userNameCommand = Base64.getEncoder().encodeToString(user.getBytes(StandardCharsets.UTF_8));
        sendCommand(userNameCommand);
        final String serverResponse2 = reader.readLine();
        logger.debug(serverResponse2);
        if (!serverResponse2.startsWith("334"))
            throw new ConnectException("While sending username command error occurred.");

        final String password = properties.getPassword();
        final String passwordCommand = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
        sendCommand(passwordCommand);
        final String serverResponse3 = reader.readLine();
        logger.debug(serverResponse3);
        if (!serverResponse3.startsWith("235"))
            throw new ConnectException("While sending username command error occurred.");

        logger.debug("AUTH command sent successfully!");

        return serverResponse3;
    }

    @Override
    public String sendMailFromCommand() throws IOException {
        logger.debug("Send MAIL FROM: command...");
        final String userEmail = properties.getUser();
        final String command = SmtpCommand.MAIL + " <" + userEmail + ">";
        sendCommand(command);
        final String serverMailResponse = reader.readLine();
        logger.debug(serverMailResponse);
        if (!serverMailResponse.startsWith("250"))
            throw new ConnectException("While sending MAIL FROM: command error occurred.");
        logger.debug("MAIL FROM command sent successfully!");
        return serverMailResponse;
    }

    @Override
    public String sendRcptToCommand(@Nonnull String[] recipients) throws IOException {
        logger.debug("Send RCPT TO: command(s)...");

        String serverResponse = "";
        for (String recipient : recipients) {
            final String command = SmtpCommand.RCPT + " <" + recipient + ">";
            sendCommand(command);
            serverResponse = reader.readLine();
            logger.debug(serverResponse);
            if (!serverResponse.startsWith("250") && !serverResponse.startsWith("251"))
                throw new ConnectException("While sending RCPT TO: command error occurred.");
        }

        logger.debug("RCPT TO: command(s) sent successfully!");
        return serverResponse;
    }

    @Override
    public void sendMessageWithoutAttachmentCommand(String message) {
        final String carriageReturn = "\r\n";

        StringBuilder command = new StringBuilder();
        command.append("MIME-Version: 1.0")
                .append(carriageReturn)
                .append("Content-Type:multipart/mixed; boundary=KkK170891tpbkKk__FV_KKKkkkjjwq")
                .append(carriageReturn)
                .append("--KkK170891tpbkKk__FV_KKKkkkjjwq")
                .append(carriageReturn)
                .append("Content-Type: text/html; charset=utf-8")
                .append(carriageReturn)
                .append("Content-Transfer-Encoding: 8bit")
                .append(carriageReturn)
                .append("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "    <head>\n" +
                        "        <meta charset=\"utf-8\">\n" +
                        "        <title></title>\n" +
                        "    </head>\n" +
                        "    <body>\n")
                .append(message)
                .append(
                                "    </body>\n" +
                                "</html>")
                .append(carriageReturn)
                .append(carriageReturn);

        sendCommand(command.toString());

        logger.debug("Message without attachment sent successfully!");
    }

    @Override
    public void sendMessageWithAttachmentCommand(String message, File[] files) throws IOException {

        final String carriageReturn = "\r\n";

        StringBuilder command = new StringBuilder();
        command.append("Content-Type:multipart/mixed;boundary=KkK170891tpbkKk__FV_KKKkkkjjwq")
                .append(carriageReturn)
                .append("--KkK170891tpbkKk__FV_KKKkkkjjwq")
                .append(carriageReturn)
                //plain/html text message
                .append("Content-Disposition: form-data; name=description")
                .append(carriageReturn)
                .append("Content-Transfer-Encoding: quoted-printable")
                .append(carriageReturn)
                .append("Content-Type:text/html; charset=UTF-8")
                .append("Content-Type: text/html; charset=utf-8")
                .append(carriageReturn)
                .append("Content-Transfer-Encoding: 8bit")
                .append(carriageReturn)
                .append("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "    <head>\n" +
                        "        <meta charset=\"utf-8\">\n" +
                        "        <title></title>\n" +
                        "    </head>\n" +
                        "    <body>\n")
                .append(message)
                .append(
                        "        <p class=\"sig\">-- <br><img src=\"cid:0123456789\"></p>\n" +
                                "    </body>\n" +
                                "</html>")
                .append(carriageReturn)
                .append(carriageReturn);

        for (File file : files) {
            String fileName = file.getName();
            byte[] fileBytesParsed = Files.readAllBytes(file.toPath());
            final String encodedFile = Base64.getEncoder().encodeToString(fileBytesParsed);
            command.append("--KkK170891tpbkKk__FV_KKKkkkjjwq")
                    .append(carriageReturn)
                    .append("Content-Type:application/octet-stream;name=")
                    .append(fileName)
                    .append(carriageReturn)
                    .append("Content-Transfer-Encoding:base64 ")
                    .append(carriageReturn)
                    .append("Content-Disposition:attachment;filename=")
                    .append(fileName)
                    .append(carriageReturn)
                    .append(encodedFile)
                    .append(carriageReturn);
        }

        command.append("--KkK170891tpbkKk__FV_KKKkkkjjwq--")
                .append(carriageReturn);

        sendCommand(command.toString());

        logger.debug("Message with attachment sent successfully!");
    }


    @Override
    public String sendDataCommand(
            @Nonnull String[] to,
            @Nonnull String[] cc,
            @Nonnull String[] bcc,
            @Nonnull String subject,
            @Nonnull String message,
            @Nonnull File[] attachFiles
    ) throws IOException {

        logger.debug("Send DATA command...");
        final String dataCommand = SmtpCommand.DATA.toString();
        sendCommand(dataCommand);

        final String serverDataResponse = reader.readLine();
        logger.debug(serverDataResponse);
        if (!serverDataResponse.startsWith("354"))
            throw new ConnectException("While sending DATA command error occurred.");

        final String dateCommand = "Date: " + LocalDateTime.now();
        sendCommand(dateCommand);

        final String fromCommand = "From: " + properties.getUser();
        sendCommand(fromCommand);

        final String subjectCommand = "Subject: " + subject;
        sendCommand(subjectCommand);

        for (String recipient : to) {
            final String toCommand = "to: " + recipient;
            sendCommand(toCommand);
        }

        for (String recipient : cc) {
            final String toCommand = "cc: " + recipient;
            sendCommand(toCommand);
        }

        for (String recipient : bcc) {
            final String toCommand = "bcc: " + recipient;
            sendCommand(toCommand);
        }

        if (attachFiles.length > 0 && attachFiles[0].isFile()) {
            sendMessageWithAttachmentCommand(message, attachFiles);
        } else {
            sendMessageWithoutAttachmentCommand(message);
        }

        final String endCommand = "\r\n.\r\n";
        sendCommand(endCommand);

        final String finalServerResponse = reader.readLine();
        logger.debug(finalServerResponse);
        if (!finalServerResponse.startsWith("250"))
            throw new ConnectException("While sending DATA command error occurred.");
        logger.debug("DATA command sent successfully!");

        return finalServerResponse;
    }

    @Override
    public String sendQuitCommand() throws IOException {
        logger.debug("End connection with smtp server...");
        final String command = SmtpCommand.QUIT.toString();
        sendCommand(command);
        final String serverQuitResponse = reader.readLine();
        logger.debug(serverQuitResponse);
        if (!serverQuitResponse.startsWith("221"))
            throw new ConnectException("While sending QUIT command error occurred.");
        logger.debug("Connection finished.");
        return serverQuitResponse;
    }

    private void sendCommand(String command) {
        writer.println(command);
        writer.flush();
    }
}

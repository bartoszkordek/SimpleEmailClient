package io.github.email.client.smtp;

import io.github.email.client.util.PropertiesLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpCommandSenderImpl implements SmtpCommandSender {

    private final PrintWriter writer;
    private final BufferedReader reader;
    private final PropertiesLoader properties;

    private final Logger logger = Logger.getLogger(SmtpCommandSenderImpl.class.getName());

    public SmtpCommandSenderImpl(
            PrintWriter writer,
            BufferedReader reader,
            PropertiesLoader propertiesLoader,
            boolean debug
    ) {
        this.writer = writer;
        this.reader = reader;
        this.properties = propertiesLoader;
        if (!debug) logger.setLevel(Level.OFF);
    }

    @Override
    public String connectionEstablished() throws IOException {
        logger.log(Level.INFO, "Check connection with smtp server...");
        final String serverReadyResponse = reader.readLine();
        logger.log(Level.INFO, serverReadyResponse);
        if (!serverReadyResponse.startsWith("220"))
            throw new ConnectException("While sending HELO command error occurred.");
        logger.log(Level.INFO, "Connection established.");
        return serverReadyResponse;
    }

    @Override
    public String sendHelloCommand() throws IOException {
        logger.log(Level.INFO, "Send HELO command...");
        final String command = SmtpCommand.HELO.toString() + " " + properties.getSmtpHost();
        sendCommand(command);
        final String serverHelloResponse = reader.readLine();
        logger.log(Level.INFO, serverHelloResponse);
        if (!serverHelloResponse.startsWith("250"))
            throw new ConnectException("While sending HELO command error occurred.");
        logger.log(Level.INFO, "HELO command sent successfully!");
        return serverHelloResponse;
    }

    @Override
    public String sendEHLOCommand() throws IOException {
        logger.log(Level.INFO, "Send EHLO command...");
        final String command = SmtpCommand.EHLO.toString() + " " + properties.getSmtpHost();
        sendCommand(command);

        String serverEhloResponse;
        while ((serverEhloResponse = reader.readLine()) != null) {
            logger.log(Level.INFO, serverEhloResponse);
            if (!serverEhloResponse.startsWith("250"))
                throw new ConnectException("While sending EHLO command error occurred.");
            if (serverEhloResponse.charAt(3) != '-') break;
        }

        logger.log(Level.INFO, "HELO command sent successfully!");
        return serverEhloResponse;
    }

    @Override
    public String sendAuthCommands() throws IOException {
        logger.log(Level.INFO, "Send AUTH command...");

        final String loginCommand = SmtpCommand.AUTH_LOGIN.toString();
        sendCommand(loginCommand);
        final String serverResponse1 = reader.readLine();
        logger.log(Level.INFO, serverResponse1);
        if (!serverResponse1.startsWith("334"))
            throw new ConnectException("While sending AUTH command error occurred.");

        final String user = properties.getUser();
        final String userNameCommand = Base64.getEncoder().encodeToString(user.getBytes(StandardCharsets.UTF_8));
        sendCommand(userNameCommand);
        final String serverResponse2 = reader.readLine();
        logger.log(Level.INFO, serverResponse2);
        if (!serverResponse2.startsWith("334"))
            throw new ConnectException("While sending username command error occurred.");

        final String password = properties.getPassword();
        final String passwordCommand = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
        sendCommand(passwordCommand);
        final String serverResponse3 = reader.readLine();
        logger.log(Level.INFO, serverResponse3);
        if (!serverResponse3.startsWith("235"))
            throw new ConnectException("While sending username command error occurred.");

        logger.log(Level.INFO, "AUTH command sent successfully!");

        return serverResponse3;
    }

    @Override
    public String sendMailFromCommand() throws IOException {
        logger.log(Level.INFO, "Send MAIL FROM: command...");
        final String userEmail = properties.getUser();
        final String command = SmtpCommand.MAIL.toString() + " <" + userEmail + ">";
        sendCommand(command);
        final String serverMailResponse = reader.readLine();
        logger.log(Level.INFO, serverMailResponse);
        if (!serverMailResponse.startsWith("250"))
            throw new ConnectException("While sending MAIL FROM: command error occurred.");
        logger.log(Level.INFO, "MAIL FROM command sent successfully!");
        return serverMailResponse;
    }

    @Override
    public String sendRcptToCommand(String[] recipients) throws IOException {
        logger.log(Level.INFO, "Send RCPT TO: command(s)...");

        String serverResponse = "";
        for (String recipient : recipients) {
            final String command = SmtpCommand.RCPT.toString() + " <" + recipient + ">";
            sendCommand(command);
            serverResponse = reader.readLine();
            logger.log(Level.INFO, serverResponse);
            if (!serverResponse.startsWith("250") && !serverResponse.startsWith("251"))
                throw new ConnectException("While sending RCPT TO: command error occurred.");
        }

        logger.log(Level.INFO, "RCPT TO: command(s) sent successfully!");
        return serverResponse;
    }

    @Override
    public String sendDataCommand(
            String[] to,
            String[] cc,
            String[] bcc,
            String subject,
            String message,
            File[] attachFiles
    ) throws IOException {

        logger.log(Level.INFO, "Send DATA command...");
        final String dataCommand = SmtpCommand.DATA.toString();
        sendCommand(dataCommand);

        final String serverDataResponse = reader.readLine();
        logger.log(Level.INFO, serverDataResponse);
        if (!serverDataResponse.startsWith("354"))
            throw new ConnectException("While sending DATA command error occurred.");

        final String dateCommand = "Date: " + LocalDateTime.now();
        sendCommand(dateCommand);

        final String fromCommand = "From: " + properties.getUser();
        sendCommand(fromCommand);

        final String subjectCommand = "Subject: " + subject;
        sendCommand(subjectCommand);

        for (String recipient : to) {
            final String toCommand = "To: " + recipient;
            sendCommand(toCommand);
        }

        //main message
        sendCommand(message);

        final String endMessage = "\r\n.\r\n";
        sendCommand(endMessage);

        final String finalServerResponse = reader.readLine();
        logger.log(Level.INFO, finalServerResponse);
        if (!finalServerResponse.startsWith("250"))
            throw new ConnectException("While sending DATA command error occurred.");
        logger.log(Level.INFO, "DATA command sent successfully!");

        return finalServerResponse;
    }

    @Override
    public String sendQuitCommand() throws IOException {
        logger.log(Level.INFO, "End connection with smtp server...");
        final String command = SmtpCommand.QUIT.toString();
        sendCommand(command);
        final String serverQuitResponse = reader.readLine();
        logger.log(Level.INFO, serverQuitResponse);
        if (!serverQuitResponse.startsWith("221"))
            throw new ConnectException("While sending QUIT command error occurred.");
        logger.log(Level.INFO, "Connection finished.");
        return serverQuitResponse;
    }

    private void sendCommand(String command) {
        writer.println(command);
        writer.flush();
    }
}

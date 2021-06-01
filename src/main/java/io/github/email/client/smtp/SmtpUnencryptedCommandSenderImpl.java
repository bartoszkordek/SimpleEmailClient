package io.github.email.client.smtp;

import io.github.email.client.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

public class SmtpUnencryptedCommandSenderImpl implements SmtpUnencryptedCommandSender {

    private final PrintWriter writer;
    private final BufferedReader reader;
    private PropertiesLoader properties;

    private final Logger logger = LoggerFactory.getLogger(SmtpUnencryptedCommandSenderImpl.class);

    public SmtpUnencryptedCommandSenderImpl(
            PrintWriter writer,
            BufferedReader reader,
            PropertiesLoader properties
    ) {
        this.writer = writer;
        this.reader = reader;
        this.properties = properties;
    }

    @Override
    public String connectionEstablished() throws IOException {
        logger.debug("Check connection with smtp server...");
        final String serverReadyResponse = reader.readLine();
        logger.debug(serverReadyResponse);
        if (!serverReadyResponse.startsWith("220"))
            throw new ConnectException("While sending HELO command error occurred.");
        logger.debug("Connection established.");
        return serverReadyResponse;
    }


    @Override
    public String sendHELOCommand() throws IOException {
        logger.debug("Send HELO command...");
        final String command = SmtpCommand.HELO + " " + properties.getSmtpHost();
        sendCommand(command);
        final String serverHelloResponse = reader.readLine();
        logger.debug(serverHelloResponse);
        if (!serverHelloResponse.startsWith("250"))
            throw new ConnectException("While sending HELO command error occurred.");
        logger.debug("HELO command sent successfully!");
        return serverHelloResponse;
    }

    @Override
    public String sendEHLOCommand() throws IOException {
        logger.debug("Send EHLO command...");
        final String command = SmtpCommand.EHLO + " " + properties.getSmtpHost();
        sendCommand(command);

        String serverEhloResponse;
        while ((serverEhloResponse = reader.readLine()) != null) {
            logger.debug(serverEhloResponse);
            if (!serverEhloResponse.startsWith("250"))
                throw new ConnectException("While sending EHLO command error occurred.");
            if (serverEhloResponse.charAt(3) != '-') break;
        }

        logger.debug("HELO command sent successfully!");
        return serverEhloResponse;
    }

    @Override
    public String sendStartTlsCommand() throws IOException {
        logger.debug("Initiating TLS handshake...");
        final String command = SmtpCommand.STARTTLS.toString();
        sendCommand(command);
        final String tlsHandshakeResponse = reader.readLine();
        logger.debug(tlsHandshakeResponse);
        if (!tlsHandshakeResponse.startsWith("220"))
            throw new ConnectException("While sending STARTTLS command error occurred.");
        logger.debug("TLS handshake done.");
        return tlsHandshakeResponse;
    }

    private void sendCommand(String command) {
        writer.println(command);
        writer.flush();
    }
}

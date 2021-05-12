package io.github.email.client.smtp;

import io.github.email.client.service.SendApi;
import io.github.email.client.util.PropertiesLoader;
import io.github.email.client.util.PropertiesLoaderImpl;

import javax.mail.MessagingException;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient implements SendApi {
    private final Logger logger = Logger.getLogger(SmtpClient.class.getName());

    public SmtpClient() {
    }

    public SmtpClient(boolean debug) {
        if (!debug) logger.setLevel(Level.OFF);
    }

    @Override
    public void sendEmail(
            Properties configProperties,
            String[] to,
            String[] cc,
            String[] bcc,
            String subject,
            String message,
            File[] attachFiles
    ) throws MessagingException, IOException, NoSuchAlgorithmException, KeyManagementException {

        logger.log(Level.INFO, "Start sending email...");
        PropertiesLoader properties = new PropertiesLoaderImpl(configProperties);

        String host = properties.getSmtpHost();
        int port = 465;// properties.getSmtpPort();

        setUpAdditionalProperties(configProperties);

        // TODO: połączenie SSL, teraz jest nieszyfrowane
//        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
//        sslContext.init(null, null, new SecureRandom());

        try (Socket socket = SSLSocketFactory.getDefault().createSocket()) {
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            int timeoutInMillis = 50 * 1000;
            socket.connect(socketAddress, timeoutInMillis);

            InputStream inputStream = socket.getInputStream();
            Reader input = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(input);

            logger.log(Level.INFO, () -> "Socket port: \t\t\t" + socket.getPort());
            logger.log(Level.INFO, () -> "Socket IP address: \t\t" + socket.getInetAddress());

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);

            checkConnection(reader);
            sendEHLOCommand(writer, reader, properties);
            sendAuthCommands(writer, reader, properties);
            sendMailFromCommand(writer, reader, properties);
            sendRcptToCommand(writer, reader, to);
            // TODO: cc  - to chyba się ustala w komendzie DATA - do weryfikacji
            // TODO: bcc  - to chyba się ustala w komendzie DATA - do weryfikacji
            sendDataCommand(writer, reader, to, cc, bcc, subject, message, properties);
            // TODO: attachedFiles  - to chyba się ustala w komendzie DATA, i chyba trzeba zakodować Base64 - do weryfikacji
            endConnection(writer, reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkConnection(BufferedReader reader) throws IOException {
        logger.log(Level.INFO, "Establish connection with smtp server...");
        String serverReadyResponse = reader.readLine();
        logger.log(Level.INFO, serverReadyResponse);
        if (!serverReadyResponse.startsWith("220")) throw new ConnectException("Establish connection go wrong");
        logger.log(Level.INFO, "Connection established.");
    }

    private void sendHelloCommand(
            PrintWriter writer,
            BufferedReader reader,
            PropertiesLoader properties
    ) throws IOException {
        logger.log(Level.INFO, "Send HELO command...");
        String command = SmtpCommand.HELO.toString() + " " + properties.getSmtpHost();
        writer.println(command);
        writer.flush();
        String serverReadyResponse = reader.readLine();
        logger.log(Level.INFO, serverReadyResponse);
        if (!serverReadyResponse.startsWith("250"))
            throw new ConnectException("While sending HELO command error occurred.");
        logger.log(Level.INFO, "HELO command sent successfully!");
    }

    private void sendEHLOCommand(
            PrintWriter writer,
            BufferedReader reader,
            PropertiesLoader properties
    ) throws IOException {
        logger.log(Level.INFO, "Send EHLO command...");
        String command = SmtpCommand.EHLO.toString() + " " + properties.getSmtpHost();
        writer.println(command);
        writer.flush();

        String serverReadyResponse;
        while ((serverReadyResponse = reader.readLine()) != null) {
            logger.log(Level.INFO, serverReadyResponse);
            if (!serverReadyResponse.startsWith("250"))
                throw new ConnectException("While sending EHLO command error occurred.");
            if (serverReadyResponse.charAt(3) != '-') break;
        }

        logger.log(Level.INFO, "HELO command sent successfully!");
    }

    private void sendAuthCommands(
            PrintWriter writer,
            BufferedReader reader,
            PropertiesLoader properties
    ) throws IOException {
        logger.log(Level.INFO, "Send AUTH command...");

        final String loginCommand = SmtpCommand.AUTH_LOGIN.toString();
        writer.println(loginCommand);
        writer.flush();
        final String serverResponse1 = reader.readLine();
        logger.log(Level.INFO, serverResponse1);
        if (!serverResponse1.startsWith("334"))
            throw new ConnectException("While sending AUTH command error occurred.");

        final String user = properties.getUser();
        final String userNameCommand = Base64.getEncoder().encodeToString(user.getBytes(StandardCharsets.UTF_8));
        writer.println(userNameCommand);
        writer.flush();
        final String serverResponse2 = reader.readLine();
        logger.log(Level.INFO, serverResponse2);
        if (!serverResponse2.startsWith("334"))
            throw new ConnectException("While sending username command error occurred.");

        final String password = properties.getPassword();
        final String passwordCommand = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
        writer.println(passwordCommand);
        writer.flush();
        final String serverResponse3 = reader.readLine();
        logger.log(Level.INFO, serverResponse3);
        if (!serverResponse3.startsWith("235"))
            throw new ConnectException("While sending username command error occurred.");

        logger.log(Level.INFO, "AUTH command sent successfully!");
    }

    private void sendMailFromCommand(PrintWriter writer, BufferedReader reader, PropertiesLoader properties) throws IOException {
        logger.log(Level.INFO, "Send MAIL FROM: command...");
        final String userEmail = properties.getUser();
        final String command = SmtpCommand.MAIL.toString() + " <" + userEmail + ">";
        writer.println(command);
        writer.flush();
        final String serverReadyResponse = reader.readLine();
        logger.log(Level.INFO, serverReadyResponse);
        if (!serverReadyResponse.startsWith("250"))
            throw new ConnectException("While sending MAIL FROM: command error occurred.");
        logger.log(Level.INFO, "MAIL FROM command sent successfully!");
    }

    private void sendRcptToCommand(PrintWriter writer, BufferedReader reader, String[] to) throws IOException {
        logger.log(Level.INFO, "Send RCPT TO: command(s)...");

        for (int i = 0; i < to.length; i++) {
            final String command = SmtpCommand.RCPT.toString() + " <" + to[i] + ">";
            writer.println(command);
            writer.flush();
            final String serverResponse = reader.readLine();
            logger.log(Level.INFO, serverResponse);
            if (!serverResponse.startsWith("250"))
                throw new ConnectException("While sending RCPT TO: command error occurred.");
        }

        logger.log(Level.INFO, "RCPT TO: command(s) sent successfully!");
    }

    private void sendDataCommand(
            PrintWriter writer,
            BufferedReader reader,
            String[] to,
            String[] cc,
            String[] bcc,
            String subject,
            String message,
            PropertiesLoader properties
    ) throws IOException {

        logger.log(Level.INFO, "Send DATA command...");
        final String dataCommand = SmtpCommand.DATA.toString();
        writer.println(dataCommand);
        writer.flush();
        final String serverDataResponse = reader.readLine();
        logger.log(Level.INFO, serverDataResponse);
        if (!serverDataResponse.startsWith("354"))
            throw new ConnectException("While sending DATA command error occurred.");

        {
            final String dateCommand = "Date: " + LocalDateTime.now();
            writer.println(dateCommand);
            writer.flush();

            final String fromCommand = "From: " + properties.getUser();
            writer.println(fromCommand);
            writer.flush();

            final String subjectCommand = "Subject: " + subject;
            writer.println(subjectCommand);
            writer.flush();

            for (String recipient : to) {
                final String toCommand = "To: " + recipient;
                writer.println(toCommand);
                writer.flush();
            }

            //main message
            writer.println(message);
            writer.flush();


            final String endMessage = "\r\n.\r\n";
            writer.println(endMessage);
            writer.flush();
        }

        final String finalServerResponse = reader.readLine();
        logger.log(Level.INFO, finalServerResponse);
        if (!finalServerResponse.startsWith("250"))
            throw new ConnectException("While sending DATA command error occurred.");
        logger.log(Level.INFO, "DATA command sent successfully!");
    }

    private void endConnection(PrintWriter writer, BufferedReader reader) throws IOException {
        logger.log(Level.INFO, "End connection with smtp server...");
        final String command = SmtpCommand.QUIT.toString();
        writer.println(command);
        writer.flush();
        final String serverReadyResponse = reader.readLine();
        logger.log(Level.INFO, serverReadyResponse);
        if (!serverReadyResponse.startsWith("221")) throw new ConnectException("Finish connection go wrong");
        logger.log(Level.INFO, "Connection finished.");
    }

    private void setUpAdditionalProperties(Properties configProperties) {
        configProperties.put("mail.smtp.ssl.trust", configProperties.getProperty("mail.smtp.host")); //trust Host
        configProperties.put("mail.smtp.ssl.enable", true);
        configProperties.put("mail.smtp.starttls.enable", false);
        configProperties.put("mail.smtp.socketFactory.port", "587");
    }
}

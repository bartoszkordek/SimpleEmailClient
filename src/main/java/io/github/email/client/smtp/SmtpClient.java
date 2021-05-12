package io.github.email.client.smtp;

import io.github.email.client.service.SendApi;
import io.github.email.client.util.PropertiesLoader;
import io.github.email.client.util.PropertiesLoaderImpl;

import javax.mail.MessagingException;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient implements SendApi {
    private final Logger logger = Logger.getLogger(SmtpClient.class.getName());
    private boolean debug;

    public SmtpClient() {
    }

    public SmtpClient(boolean debug) {
        this.debug = debug;
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

            SmtpCommandSender smtpCommandSender = new SmtpCommandSenderImpl(writer, reader, properties, debug);

            smtpCommandSender.connectionEstablished();
            smtpCommandSender.sendEHLOCommand();
            smtpCommandSender.sendAuthCommands();
            smtpCommandSender.sendMailFromCommand();
            // TODO: chyba powinni byś wszyscy przekazani
            smtpCommandSender.sendRcptToCommand(to);
            // TODO: cc  - to chyba się ustala w komendzie DATA - do weryfikacji
            // TODO: bcc  - to chyba się ustala w komendzie DATA - do weryfikacji
            smtpCommandSender.sendDataCommand(to, cc, bcc, subject, message, attachFiles);
            // TODO: attachedFiles  - to chyba się ustala w komendzie DATA, i chyba trzeba zakodować Base64 - do weryfikacji
            smtpCommandSender.sendQuitCommand();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setUpAdditionalProperties(Properties configProperties) {
        configProperties.put("mail.smtp.ssl.trust", configProperties.getProperty("mail.smtp.host")); //trust Host
        configProperties.put("mail.smtp.ssl.enable", true);
        configProperties.put("mail.smtp.starttls.enable", false);
        configProperties.put("mail.smtp.socketFactory.port", "587");
    }
}

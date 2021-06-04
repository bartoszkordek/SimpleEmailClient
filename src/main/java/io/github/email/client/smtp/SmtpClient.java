package io.github.email.client.smtp;

import io.github.email.client.service.SSLUtils;
import io.github.email.client.service.SendApi;
import io.github.email.client.util.PropertiesLoader;
import io.github.email.client.util.PropertiesLoaderImpl;
import javafx.scene.control.ProgressBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Properties;

public class SmtpClient implements SendApi {
    private final Logger logger = LoggerFactory.getLogger(SmtpClient.class);

    private String[] joinAllRecipients(@Nonnull String[] to, @Nonnull String[] cc, @Nonnull String[] bcc) {
        String[] joinedRecipients = new String[to.length + cc.length + bcc.length];
        System.arraycopy(to, 0, joinedRecipients, 0, to.length);
        System.arraycopy(cc, 0, joinedRecipients, to.length, cc.length);
        System.arraycopy(bcc, 0, joinedRecipients, to.length + cc.length, bcc.length);
        return joinedRecipients;
    }

    @Override
    public void sendEmail(
            @Nonnull Properties configProperties,
            @Nonnull String[] to,
            @Nonnull String[] cc,
            @Nonnull String[] bcc,
            @Nonnull String subject,
            @Nonnull String message,
            @Nonnull File[] attachFiles,
            ProgressBar progressBar
    ) {

        logger.debug("Start sending email...");
        PropertiesLoader properties = new PropertiesLoaderImpl(configProperties);

        String host = properties.getSmtpHost();
        int port = properties.getSmtpPort();

        SSLUtils.disableChecking();

        try (Socket socket = SocketFactory.getDefault().createSocket()) {
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            progressBar.setProgress(0.1);
            int timeoutInMillis = 50 * 1000;
            socket.connect(socketAddress, timeoutInMillis);

            logger.debug("Unencrypted socket port: \t\t\t" + socket.getPort());
            logger.debug("Unencrypted socket IP address: \t\t" + socket.getInetAddress());

            try (PrintWriter writer = new PrintWriter(socket.getOutputStream());
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                SmtpUnencryptedCommandSender unencryptedCommandSender = new SmtpUnencryptedCommandSenderImpl(writer, reader, properties);
                progressBar.setProgress(0.2);
                unencryptedCommandSender.connectionEstablished();
                progressBar.setProgress(0.3);
                unencryptedCommandSender.sendEHLOCommand();
                progressBar.setProgress(0.4);
                unencryptedCommandSender.sendStartTlsCommand();
                progressBar.setProgress(0.5);

                try (SSLSocket sslSocket = (SSLSocket) ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(
                        socket, socket.getInetAddress().getHostAddress(), socket.getPort(), true);
                     PrintWriter sslWriter = new PrintWriter(sslSocket.getOutputStream());
                     BufferedReader sslReader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()))) {

                    SmtpSSLCommandSender smtpSSLCommandSender = new SmtpSSLCommandSenderImpl(sslWriter, sslReader, properties);
                    smtpSSLCommandSender.sendAuthCommands();
                    progressBar.setProgress(0.6);
                    smtpSSLCommandSender.sendMailFromCommand();
                    progressBar.setProgress(0.7);
                    String[] joinedAllRecipients = joinAllRecipients(to, cc, bcc);
                    smtpSSLCommandSender.sendRcptToCommand(joinedAllRecipients);
                    progressBar.setProgress(0.8);
                    smtpSSLCommandSender.sendDataCommand(to, cc, bcc, subject, message, attachFiles);
                    progressBar.setProgress(0.9);
                    smtpSSLCommandSender.sendQuitCommand();
                    progressBar.setProgress(1.0);
                }
            }
        } catch (Exception e) {
            progressBar.setProgress(1.0);
            e.printStackTrace();
        }
    }
}

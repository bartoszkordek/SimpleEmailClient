package io.github.email.client.smtp;

import io.github.email.client.service.SendApi;

import javax.mail.MessagingException;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

public class SmtpClient implements SendApi {

    @Override
    public void sendEmail(Properties configProperties, String[] to, String[] cc, String[] bcc,
                          String subject, String message, File[] attachFiles) throws MessagingException, IOException {
        // TODO implement (Grzegorz/Bartosz)
        System.out.println("Hello");
        String host = configProperties.getProperty("mail.smtp.host");
        String port = configProperties.getProperty("mail.smtp.port");
        String user = configProperties.getProperty("mail.user");
        String password = configProperties.getProperty("mail.password");
        configProperties.put("mail.smtp.ssl.trust", configProperties.getProperty("mail.smtp.host")); //trust Host
        try (Socket socket = SSLSocketFactory.getDefault().createSocket()) {
            socket.connect(new InetSocketAddress(host, Integer.parseInt(port)), 5 * 1000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(reader.readLine());
            String commandSent = "HELO";
            writer.println(commandSent);
            writer.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

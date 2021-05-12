package io.github.email.client.smtp;

import io.github.email.client.imap.CommandResponse;
import io.github.email.client.service.SendApi;

import javax.mail.MessagingException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SmtpClient implements SendApi {

    private long commandCounter = 1;
    private final boolean debug;
    public SmtpClient(boolean debug) {
        this.debug = debug;
    }

    @Override
    public void sendEmail(Properties configProperties, String[] to, String[] cc, String[] bcc,
                          String subject, String message, File[] attachFiles) throws MessagingException, IOException, NoSuchAlgorithmException, KeyManagementException {
        // TODO implement (Grzegorz/Bartosz)
        System.out.println("Hello");
        String host = configProperties.getProperty("mail.smtp.host");
        String port = configProperties.getProperty("mail.smtp.port");
        String user = configProperties.getProperty("mail.user");
        String password = configProperties.getProperty("mail.password");
        configProperties.put("mail.smtp.ssl.trust", configProperties.getProperty("mail.smtp.host")); //trust Host
        configProperties.put("mail.smtp.ssl.enable", true);
        configProperties.put("mail.smtp.starttls.enable", false);
        configProperties.put("mail.smtp.socketFactory.port", "465");
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, new SecureRandom());

        try (Socket socket = SSLSocketFactory.getDefault().createSocket()) {
            socket.connect(new InetSocketAddress("smtp.gmail.com", Integer.parseInt("465")), 50 * 1000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(socket.getPort());
            System.out.println(socket.getInetAddress());

            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            String commandSent = "ELHO smtp.gmail.com"; //sample hello request message

            handshake(writer, reader, commandSent);

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private void handshake(PrintWriter writer, BufferedReader reader, String command) throws IOException {
        CommandResponse response = sendCommand(writer, reader, command);
        System.out.println("RESPONSE: " + response.getConfirmation().length());
    }


    private void login(PrintWriter writer, BufferedReader reader,
                       String user, String password) throws IOException {
        System.out.println(reader.read());
        CommandResponse response = sendCommand(writer, reader, "AUTH LOGIN ");
        if (!response.getConfirmation().contains("OK")) {
            throw new IOException("Login failed");
        }
    }
    private CommandResponse sendCommand(PrintWriter writer, BufferedReader reader, String command) throws IOException {
        // send to server
        String commandSent = command;
        writer.println(commandSent);
        writer.flush();
        // collect responses from server
        List<String> responseLines = new ArrayList<>();
        String confirmation = "";
        String line;
        System.out.println(reader.readLine());
        while ((line = reader.readLine()) != null) {
            String lineTrimmed = line.trim();
            String[] chunks = lineTrimmed.split("\\s+");
            if (chunks.length > 0) {
                String returnedCounter = chunks[0];
                if (returnedCounter.equals(getCounter())) {
                    // last line
                    confirmation = line;
                    break;
                } else {
                    responseLines.add(line);
                }
            } else {
                responseLines.add(line);
            }
        }
        CommandResponse response = new CommandResponse(responseLines, confirmation);
        if (debug) {
            printDebugInfo(response, commandSent);
        }
        commandCounter++;
        return response;
    }

    private String getCounter() {
        return String.format("%09d", commandCounter);
    }

    private void printDebugInfo(CommandResponse response, String command) {
        System.out.println();
        System.out.println("############|-START-|############");
        System.out.println(command);
        response.getLines().forEach(System.out::println);
        System.out.println(response.getConfirmation());
        System.out.println("#############|-END-|#############");
        System.out.println();
    }
}

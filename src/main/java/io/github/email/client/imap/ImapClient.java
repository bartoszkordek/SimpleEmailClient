package io.github.email.client.imap;

import io.github.email.client.service.ReceiveApi;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ImapClient implements ReceiveApi {
    private long commandCounter = 1;
    private final boolean debug;
    private final BodyStructureParser bodyStructureParser = new BodyStructureParser();

    public ImapClient() {
        this(false);
    }
    public ImapClient(boolean debug) {
        this.debug = debug;
    }

    @Override
    public List<MailMetadata> downloadEmails(Properties properties, int limit) {
        String host = properties.getProperty("mail.imap.host");
        String port = properties.getProperty("mail.imap.port");
        String user = properties.getProperty("mail.user");
        String password = properties.getProperty("mail.password");
        try (Socket socket = SSLSocketFactory.getDefault().createSocket()) {
            socket.connect(new InetSocketAddress(host, Integer.parseInt(port)), 5 * 1000);
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream());
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                // Hello from server
                System.out.println(reader.readLine());
                // Login
                login(writer, reader, user, password);
                selectInbox(writer, reader);
                List<Integer> mailIds = searchAll(writer, reader);
                List<MailMetadata> mailMetadatas = new ArrayList<>();
                for (int i = 0; i < Math.min(limit, mailIds.size()); i++) {
                    int mailId = mailIds.get(i);
                    MailMetadata mailMetadata = fetchMetadata(writer, reader, mailId);
                    System.out.println(i + ". email downloaded (low level API - using IMAP directly)");
                    mailMetadatas.add(mailMetadata);
                }
                return mailMetadatas;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void login(PrintWriter writer, BufferedReader reader,
                                  String user, String password) throws IOException {
        CommandResponse response = sendCommand(writer, reader, "LOGIN " + user + " " + password);
        if (!response.getConfirmation().contains("OK")) {
            throw new IOException("Login failed");
        }
    }

    private void selectInbox(PrintWriter writer, BufferedReader reader) throws IOException {
        CommandResponse response = sendCommand(writer, reader, "SELECT INBOX");
        if (!response.getConfirmation().contains("OK")) {
            throw new IOException("Selecting inbox failed");
        }
    }

    private List<Integer> searchAll(PrintWriter writer, BufferedReader reader) throws IOException {
        CommandResponse response = sendCommand(writer, reader, "SEARCH ALL");
        if (!response.getConfirmation().contains("OK")) {
            throw new IOException("Listing messages failed");
        }
        List<String> lines = response.getLines();
        if (lines.size() > 0) {
            // this line should look like "* SEARCH 1 2 3 4 5 6 7 8 9 10"
            String lineWithUids = response.getLines().get(0);
            String[] chunks = lineWithUids.split("\\s+");
            // discarding first two elements
            return Arrays.asList(chunks)
                    .subList(2, chunks.length)
                    .stream()
                    .map(Integer::parseInt)
                    .sorted(Comparator.comparingInt((Integer id) -> id).reversed())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private MailMetadata fetchMetadata(PrintWriter writer, BufferedReader reader, int id) throws IOException {
        CommandResponse response = sendCommand(writer, reader, "FETCH " + id +
                " (BODY[HEADER.FIELDS (SUBJECT DATE FROM TO CC BCC)])");
        if (!response.getConfirmation().contains("OK")) {
            throw new IOException("Fetching headers for ID " + id + " failed");
        }
        String date = "", from = "", to = "", cc = "", bcc = "", subject = "";
        for (String line : response.getLines()) {
            if (line.startsWith("Date: ")) {
                date = line.substring(6);
            } else if (line.startsWith("From: ")) {
                from = line.substring(6);
            } else if (line.startsWith("To: ")) {
                to = line.substring(4);
            } else if (line.startsWith("Cc: ")) {
                cc = line.substring(4);
            } else if (line.startsWith("Bcc: ")) {
                bcc = line.substring(5);
            } else if (line.startsWith("Subject: ")) {
                subject = line.substring(9);
            }
        }
        List<MailContentPart> parts = fetchBodyStructure(writer, reader, id);
        List<Attachment> attachments = new ArrayList<>();
        String bodyPlain = "";
        String bodyHtml = "";
        for (MailContentPart part : parts) {
            String body = fetchBodyPart(writer, reader, id, part.getPartNum());
            byte[] bodyBytes;
            // TODO support QUOTED-PRINTABLE?
            if (part.getTokenText().contains("BASE64")) {
                bodyBytes = Base64.getDecoder().decode(body.replaceAll("\n", ""));
            } else {
                bodyBytes = body.getBytes(StandardCharsets.UTF_8);
            }
            if (part.getTokenText().contains("ATTACHMENT")) {
                attachments.add(new Attachment(bodyBytes, part.getType(), getFileName(part.getTokenText())));
            } else if (part.getType().equals("PLAIN")) {
                bodyPlain = new String(bodyBytes, StandardCharsets.UTF_8);
            } else if (part.getType().equals("HTML")){
                bodyHtml = new String(bodyBytes, StandardCharsets.UTF_8);
            } // discard the rest
        }
        return new MailMetadata(date, from, to, cc, bcc, subject, bodyPlain, bodyHtml, attachments);
    }

    private String getFileName(String textToken) {
        Pattern pattern = Pattern.compile(".*\"ATTACHMENT\" \\(\"FILENAME\" \"(.*)\"\\).*");
        Matcher matcher = pattern.matcher(textToken);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "unknownFile";
    }

    private List<MailContentPart> fetchBodyStructure(PrintWriter writer, BufferedReader reader, int id) throws IOException {
        CommandResponse response = sendCommand(writer, reader, "FETCH " + id + " (BODYSTRUCTURE)");
        if (!response.getConfirmation().contains("OK")) {
            throw new IOException("Getting BODYSTRUCTURE for " + id + " failed");
        }
        assert response.getLines().size() == 1;
        return bodyStructureParser.parseMailContent(response.getLines().get(0));
    }

    private String fetchBodyPart(PrintWriter writer, BufferedReader reader, int id, String partNum) throws IOException {
        CommandResponse response = sendCommand(writer, reader, "FETCH " + id + " (BODY[" + partNum + "])");
        if (!response.getConfirmation().contains("OK")) {
            throw new IOException("Fetching body part " + partNum + " with ID " + id + " failed");
        }
        String res = String.join("\n", response.getLines().subList(1, response.getLines().size()));
        return res.substring(0, res.length()-1);
    }

    private CommandResponse sendCommand(PrintWriter writer, BufferedReader reader, String command) throws IOException {
        // send to server
        String commandSent = getCounter() + " " + command + "\r";
        writer.println(commandSent);
        writer.flush();
        // collect responses from server
        List<String> responseLines = new ArrayList<>();
        String confirmation = "";
        String line;
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

    private void printDebugInfo(CommandResponse response, String command) {
        System.out.println();
        System.out.println("############|-START-|############");
        System.out.println(command);
        response.getLines().forEach(System.out::println);
        System.out.println(response.getConfirmation());
        System.out.println("#############|-END-|#############");
        System.out.println();
    }

    private String getCounter() {
        return String.format("%09d", commandCounter);
    }
}

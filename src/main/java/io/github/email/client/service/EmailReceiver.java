package io.github.email.client.service;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

public class EmailReceiver {

    private Properties getServerProperties(String protocol, String host,
            String port) {
        Properties properties = new Properties();
 
        // server setting
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
 
        // SSL setting
        properties.setProperty(
                String.format("mail.%s.socketFactory.class", protocol),
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback", protocol),
                "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port", protocol),
                String.valueOf(port));

        properties.put("mail.transport.protocol", "imap");
        properties.put("mail.imap.auth", "true");
        properties.put("mail.imap.ssl.checkserveridentity", "false");
        properties.put("mail.imap.ssl.trust", "*");
 
        return properties;
    }

    public String[][] downloadEmails(String protocol, String host, String port,
            String userName, String password) {
        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);
 
        try {
            // connects to the message store
            Store store = session.getStore(protocol);
            store.connect(userName, password);
 
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
 
            // fetches new messages from server
            Message[] messages = folderInbox.getMessages();
            String[][] messageFields = new String[10][5];
            int counter = 0;
            for (int i = messages.length-1; i >= 0; i--) {
                Message msg = messages[i];
                Address[] fromAddress = msg.getFrom();
                String from = fromAddress[0].toString();
                String subject = msg.getSubject();
                String toList = parseAddresses(msg
                        .getRecipients(RecipientType.TO));
                String ccList = parseAddresses(msg
                        .getRecipients(RecipientType.CC));
                String sentDate = msg.getSentDate().toString();

                System.out.println("email downloaded");
                // TODO do it properly
                messageFields[counter][0] = from;
                messageFields[counter][1] = toList;
                messageFields[counter][2] = ccList;
                messageFields[counter][3] = subject;
                messageFields[counter][4] = sentDate;
                counter++;
                if (counter == 10) {
                    break;
                }
            }
 
            // disconnect
            folderInbox.close(false);
            store.close();
            return messageFields;
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: " + protocol);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        }
        return null;
    }

    private String parseAddresses(Address[] address) {
        StringBuilder listAddress = new StringBuilder();
        if (address != null) {
            for (Address value : address) {
                listAddress.append(value.toString()).append(", ");
            }
        }
        if (listAddress.length() > 1) {
            listAddress = new StringBuilder(listAddress.substring(0, listAddress.length() - 2));
        }
        return listAddress.toString();
    }
}
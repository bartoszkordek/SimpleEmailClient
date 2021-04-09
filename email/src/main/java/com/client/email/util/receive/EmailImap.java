package com.client.email.util.receive;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.*;
import java.util.Properties;

public class EmailImap {

    public static void saveParts(Object content, String filename)
            throws IOException, MessagingException
    {
        OutputStream out = null;
        InputStream in = null;
        try {
            if (content instanceof Multipart) {
                Multipart multi = ((Multipart)content);
                int parts = multi.getCount();
                for (int j=0; j < parts; ++j) {
                    MimeBodyPart part = (MimeBodyPart)multi.getBodyPart(j);
                    if (part.getContent() instanceof Multipart) {
                        // part-within-a-part, do some recursion...
                        saveParts(part.getContent(), filename);
                    }
                    else {
                        String extension = "";
                        if (part.isMimeType("text/html")) {
                            extension = "html";
                        }
                        else {
                            if (part.isMimeType("text/plain")) {
                                extension = "txt";
                            }
                            else {
                                //  Try to get the name of the attachment
                                extension = part.getDataHandler().getName();
                            }
                            filename = filename + "." + extension;
                            System.out.println("... " + filename);
                            out = new FileOutputStream(new File(filename));
                            in = part.getInputStream();
                            int k;
                            while ((k = in.read()) != -1) {
                                out.write(k);
                            }
                        }
                    }
                }
            }
        }
        finally {
            if (in != null) { in.close(); }
            if (out != null) { out.flush(); out.close(); }
        }
    }

    public static void receive() throws MessagingException {
        Folder folder = null;
        Store store = null;

        try{
            Properties properties = new Properties();
            properties.put("mail.store.protocol","imaps");
            properties.put("mail.smtp.tls.trust", "smtp.poczta.onet.pl");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.imap.auth", "true");
            properties.put("mail.imap.socketFactory.port", 993);
            properties.put("mail.imap.socketFactory.fallback", "false");

            Session session = Session.getDefaultInstance(properties, null);
            store = session.getStore("imaps");
            store.connect("imap.poczta.onet.pl","sample_email@op.pl", "password");

            folder = store.getFolder("Inbox");
            folder.open(Folder.READ_WRITE);
            Message messages[] = folder.getMessages();
            System.out.println("No of Messages : " + folder.getMessageCount());
            System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
            for (int i=0; i < messages.length; ++i) {
                System.out.println("MESSAGE #" + (i + 1) + ":");
                Message msg = messages[i];

                String from = "unknown";
                if (msg.getReplyTo().length >= 1) {
                    from = msg.getReplyTo()[0].toString();
                }
                else if (msg.getFrom().length >= 1) {
                    from = msg.getFrom()[0].toString();
                }
                String subject = msg.getSubject();
                System.out.println("Saving ... " + subject +" " + from);

                String filename = "C:/Users/sample_folder" +  subject;
                saveParts(msg.getContent(), filename);
                msg.setFlag(Flags.Flag.SEEN,true);
                // to delete the message
                // msg.setFlag(Flags.Flag.DELETED, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            if (folder != null) { folder.close(true); }
            if (store != null) { store.close(); }
        }
    }
}

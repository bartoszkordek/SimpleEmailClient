package io.github.email.client.service;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class EmailService {
	public static void sendEmail(Properties configProperties, String[] to, String[] cc,
			String subject, String message, File[] attachFiles)
			throws MessagingException, IOException {

		final String userName = configProperties.getProperty("mail.user");
		final String password = configProperties.getProperty("mail.password");
		
		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(configProperties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = Arrays.stream(to).map(a -> {
			try {
				return new InternetAddress(a);
			} catch (AddressException e) {
				e.printStackTrace();
				return null;
			}
		}).toArray(InternetAddress[]::new);
		InternetAddress[] ccs = Arrays.stream(cc).map(a -> {
			try {
				return new InternetAddress(a);
			} catch (AddressException e) {
				e.printStackTrace();
				return null;
			}
		}).toArray(InternetAddress[]::new);
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setRecipients(Message.RecipientType.CC, ccs);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachFiles != null && attachFiles.length > 0) {
			for (File aFile : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				attachPart.attachFile(aFile);

				multipart.addBodyPart(attachPart);
			}
		}

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);
	}

	public static String[][] downloadEmails(Properties properties) {
		Session session = Session.getDefaultInstance(properties);
		String protocol = properties.getProperty("mail.transport.protocol");
		String username = properties.getProperty("mail.user");
		String password = properties.getProperty("mail.password");

		try {
			// connects to the message store
			Store store = session.getStore(protocol);
			store.connect(username, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_ONLY);

			// fetches new messages from server
			Message[] messages = folderInbox.getMessages();
			String[][] messageFields = new String[25][5];
			int counter = 0;
			for (int i = messages.length-1; i >= 0; i--) {
				Message msg = messages[i];
				Address[] fromAddress = msg.getFrom();
				String from = fromAddress[0].toString();
				String subject = msg.getSubject();
				String toList = parseAddresses(msg
						.getRecipients(Message.RecipientType.TO));
				String ccList = parseAddresses(msg
						.getRecipients(Message.RecipientType.CC));
				String sentDate = msg.getSentDate().toString();

				System.out.println((counter+1) + ". Email downloaded");
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

	private static String parseAddresses(Address[] address) {
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
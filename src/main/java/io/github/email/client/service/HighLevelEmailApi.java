package io.github.email.client.service;

import io.github.email.client.imap.MailMetadata;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class HighLevelEmailApi implements EmailApi {
	public void sendEmail(Properties configProperties, String[] to, String[] cc, String[] bcc,
						  String subject, String message, File[] attachFiles) throws MessagingException, IOException {

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
		msg.setRecipients(Message.RecipientType.TO, getInternetAddresses(to));
		if (cc != null && cc.length > 0) {
			msg.setRecipients(Message.RecipientType.CC, getInternetAddresses(cc));
		}
		if (bcc != null && bcc.length > 0) {
			msg.setRecipients(Message.RecipientType.BCC, getInternetAddresses(bcc));
		}
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
			for (File file : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				attachPart.attachFile(file);

				multipart.addBodyPart(attachPart);
			}
		}

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);
	}

	private InternetAddress[] getInternetAddresses(String[] addresses) {
		return Arrays.stream(addresses).map(address -> {
			try {
				return new InternetAddress(address);
			} catch (AddressException e) {
				e.printStackTrace();
				return null;
			}
		}).toArray(InternetAddress[]::new);
	}

	public List<MailMetadata> downloadEmails(Properties properties, int limit) {
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
			List<Message> messages = Arrays.stream(folderInbox.getMessages()).collect(Collectors.toList());
			Collections.reverse(messages);
			List<MailMetadata> metadatas = new ArrayList<>();
			for (int i = 0; i < Math.min(limit, messages.size()); i++) {
				Message message = messages.get(i);
				String from = parseAddresses(message.getFrom());
				String subject = message.getSubject();
				String toList = parseAddresses(message
						.getRecipients(Message.RecipientType.TO));
				String ccList = parseAddresses(message
						.getRecipients(Message.RecipientType.CC));
				String bccList = parseAddresses(message
						.getRecipients(Message.RecipientType.BCC));
				String sentDate = message.getSentDate().toString();


				System.out.println(i + ". Email downloaded");
				metadatas.add(new MailMetadata(sentDate, from, toList, ccList, bccList, subject));

//				String contentType = message.getContentType();
//				String messageContent = "";
//				if (contentType.contains("multipart")) {
//					Multipart multiPart = (Multipart) message.getContent();
//					int numberOfParts = multiPart.getCount();
//					for (int partCount = 0; partCount < numberOfParts; partCount++) {
//						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
//						messageContent = part.getContent().toString();
//					}
//				}
//				else if (contentType.contains("text/plain")
//						|| contentType.contains("text/html")) {
//					Object content = message.getContent();
//					if (content != null) {
//						messageContent = content.toString();
//					}
//				}
			}

			// disconnect
			folderInbox.close(false);
			store.close();
			return metadatas;
		} catch (NoSuchProviderException ex) {
			System.err.println("No provider for protocol: " + protocol);
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.err.println("Could not connect to the message store");
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
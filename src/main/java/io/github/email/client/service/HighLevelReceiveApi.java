package io.github.email.client.service;

import io.github.email.client.imap.Attachment;
import io.github.email.client.imap.MailMetadata;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class HighLevelReceiveApi implements ReceiveApi {

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

				String contentType = message.getContentType();
				List<Attachment> attachments = new ArrayList<>();
				String bodyPlain = "";
				String bodyHtml = "";
				if (contentType.contains("multipart")) {
					Multipart multiPart = (Multipart) message.getContent();
					for (int j = 0; j < multiPart.getCount(); j++) {
						// TODO probably not all of them are attachments
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
						attachments.add(new Attachment(part.getContent().toString().getBytes(), part.getContentType(), "file" + j));
					}
				} else if (contentType.contains("text/plain")) {
					Object content = message.getContent();
					if (content != null) {
						bodyPlain = content.toString();
					}
				} else if (contentType.contains("text/html")) {
					Object content = message.getContent();
					if (content != null) {
						bodyHtml = content.toString();
					}
				}
				System.out.println(i + ". email downloaded (high level API)");
				metadatas.add(new MailMetadata(sentDate, from, toList, ccList, bccList, subject, bodyPlain, bodyHtml, attachments));
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
		} catch (IOException e) {
			e.printStackTrace();
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
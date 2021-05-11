package io.github.email.client.service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
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

public class HighLevelSendApi implements SendApi {

	public void sendEmail(Properties configProperties, String[] to, String[] cc, String[] bcc,
						  String subject, String message, File[] attachFiles) throws MessagingException, IOException {

		System.out.println("HHigh level ello");
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
}
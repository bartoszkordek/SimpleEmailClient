package io.github.email.client.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigService {
	private final File configFile = new File("config.properties");
	private final Properties properties = new Properties();

	public ConfigService()  {
		if (configFile.exists()) {
			try (InputStream inputStream = new FileInputStream(configFile)) {
				properties.load(inputStream);
			} catch (IOException e) {
				throw new IllegalStateException("Cannot load properties from file");
			}
		} else {
			setDefaultProperties();
		}
	}

	public Properties getProperties() {
		return properties;
	}
	

	public void saveProperties(String smtpHost, String imapHost, String smtpPort, String imapPort, String user, String pass) throws IOException {
		setProperties(smtpHost, imapHost, smtpPort, imapPort, user, pass);
		OutputStream outputStream = new FileOutputStream(configFile);
		properties.store(outputStream, "Email settings");
		outputStream.close();
	}

	private void setDefaultProperties() {
		setProperties("smtp.gmail.com", "imap.gmail.com", "587", "993", "mail@gmail.com", "pass");
	}

	private void setProperties(String smtpHost, String imapHost, String smtpPort, String imapPort, String user, String pass) {

		properties.setProperty("mail.smtp.host", smtpHost);
		properties.setProperty("mail.smtp.port", smtpPort);
		properties.setProperty("mail.user", user);
		properties.setProperty("mail.password", pass);
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.setProperty("mail.imap.host", imapHost);
		properties.setProperty("mail.imap.port", imapPort);
		properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port", "993");
		properties.setProperty("mail.transport.protocol", "imap");
		properties.setProperty("mail.imap.auth", "true");
		properties.setProperty("mail.imap.ssl.checkserveridentity", "false");
		properties.setProperty("mail.imap.ssl.trust", "*");


	}
}